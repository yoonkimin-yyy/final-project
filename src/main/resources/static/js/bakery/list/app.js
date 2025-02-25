var map;
var markers = [];

// 사용자의 현재 위치 가져오기
function getUserLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var lat = position.coords.latitude;
            var lng = position.coords.longitude;
            var locPosition = new kakao.maps.LatLng(lat, lng);
            
            initMap(locPosition);
            searchNearbyBakeries(locPosition);
        }, function(error) {
            alert('위치 정보를 가져올 수 없습니다.');
        });
    } else {
        alert('GPS를 지원하지 않습니다.');
    }
}

// 지도 초기화
function initMap(centerPosition) {
    var mapContainer = document.getElementById('map');
    var mapOption = { 
        center: centerPosition,
        level: 5
    };

    map = new kakao.maps.Map(mapContainer, mapOption);
}

// 근처 빵집 검색
function searchNearbyBakeries(locPosition) {
    var ps = new kakao.maps.services.Places();
    var keyword = "빵집";
    
    var searchOption = {
        location: locPosition,
        radius: 2000
    };

    ps.keywordSearch(keyword, function(data, status, pagination) {
        if (status === kakao.maps.services.Status.OK) {
            displayPlaceList(data);
            displayMarkers(data);
        } else {
            alert("주변 빵집을 찾을 수 없습니다.");
        }
    }, searchOption);
}

// 검색된 빵집 리스트 표시
function displayPlaceList(places) {
    var listEl = document.getElementById('placeList');
    listEl.innerHTML = ''; // 기존 리스트 초기화

    places.forEach(function(place, index) {
        var listItem = document.createElement('li');
        listItem.className = 'place-item';
        listItem.innerHTML = `
            <strong>${place.place_name}</strong><br>
            📍 ${place.address_name}<br>
            🚶 거리: ${Math.floor(place.distance)}m
        `;
        
        listItem.onclick = function() {
            moveToLocation(place.y, place.x, place.place_name);
        };
        
        listEl.appendChild(listItem);
    });
}

// 지도 마커 표시
function displayMarkers(places) {
    clearMarkers(); // 기존 마커 삭제

    places.forEach(function(place) {
        var marker = new kakao.maps.Marker({
            map: map,
            position: new kakao.maps.LatLng(place.y, place.x)
        });

        kakao.maps.event.addListener(marker, 'click', function() {
            var infowindow = new kakao.maps.InfoWindow({
                content: `<div style="padding:5px;">${place.place_name}</div>`
            });
            infowindow.open(map, marker);
        });

        markers.push(marker);
    });
}

// 마커 초기화
function clearMarkers() {
    markers.forEach(function(marker) {
        marker.setMap(null);
    });
    markers = [];
}

// 선택한 위치로 지도 이동
function moveToLocation(lat, lng, name) {
    var moveLatLon = new kakao.maps.LatLng(lat, lng);
    map.panTo(moveLatLon);
}

// 리스트 정렬 기능
function sortPlaceList() {
    var sortType = document.getElementById('sortSelect').value;
    var listEl = document.getElementById('placeList');
    var placeItems = Array.from(listEl.getElementsByClassName('place-item'));

    placeItems.sort(function(a, b) {
        if (sortType === 'distance') {
            return parseInt(a.dataset.distance) - parseInt(b.dataset.distance);
        }
        // 평점순 정렬 예시 (평점 데이터가 있을 경우)
        else if (sortType === 'rating') {
            return parseFloat(b.dataset.rating) - parseFloat(a.dataset.rating);
        }
    });

    // 정렬된 요소 다시 추가
    listEl.innerHTML = '';
    placeItems.forEach(function(item) {
        listEl.appendChild(item);
    });
}
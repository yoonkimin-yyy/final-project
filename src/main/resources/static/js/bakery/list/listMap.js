navigator.geolocation.getCurrentPosition(function(position) {
    var lat = position.coords.latitude; // 위도
    var lng = position.coords.longitude; // 경도
    var locPosition = new kakao.maps.LatLng(lat, lng); // 좌표 생성

    console.log(lat, lng);

    // 지도에 현재 위치를 표시
    displayCurrentLocation(locPosition);
}, function(error) {
    alert('위치 정보를 가져올 수 없습니다.');
});
// 현재 위치 마커 추가가
function displayCurrentLocation(locPosition) {
    var mapContainer = document.getElementById('map'); // 지도를 표시할 div
    var mapOption = { 
        center: locPosition, // 현재 위치를 중심으로 지도 설정
        level: 5 // 확대 레벨
    };
    
    // 지도 생성
    var map = new kakao.maps.Map(mapContainer, mapOption); 
    
	// 야구공모양 마커주소
	var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png';
	var imageSize = new kakao.maps.Size(64, 69); // 마커이미지의 크기
	var imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션.
	// 내위치 야구공모양 마커주소
	var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption)
    // 내현재 위치를 표시할 마커 야구공 모양으로 생성
    var marker = new kakao.maps.Marker({
        map: map,
        position: locPosition,
        title: '내 위치',
		image : markerImage
    });

    // 내 현재 위치에 '내 위치'라는 인포윈도우 생성
    var infowindow = new kakao.maps.InfoWindow({
        content: '<div style="padding:5px;">내 위치</div>',
        removable: true
    });
    infowindow.open(map, marker);
	
	$.ajax({
	               url: '/api/list',
	               type: "GET",
				   data:{
						searchText: $("#searchText").val(),
						orderType: $("#filter-select").val()
				   },
	               dataType: "json",
	               success: function (response) {
	                   response.posts.forEach(function (bakery) {
	                       var coords = new kakao.maps.LatLng(bakery.bakeryLat, bakery.bakeryLog);
	                       
	                       var marker = new kakao.maps.Marker({
	                           map: map,
	                           position: coords
	                       });

	                       var infowindow = new kakao.maps.InfoWindow({
	                           content: `<div style="padding:5px;">${bakery.bakeryName}<br>${bakery.bakeryAddress}</div>`
	                       });

	                       kakao.maps.event.addListener(marker, 'click', function () {
	                           infowindow.open(map, marker);
	                       });
	                   });
	               },
	               error: function (xhr, status, error) {
	                   console.error("데이터 가져오기 실패:", error);
	               }
	           });
}		   
	
	

    // 내 위치 주변 빵집 검색
/*    searchNearbyBakeries(map, locPosition);*/


/*// 내 위치 주변 빵집 검색
function searchNearbyBakeries(map, locPosition) {
    var ps = new kakao.maps.services.Places(); // 장소 검색 객체 생성
    
    var keyword = "빵집"; // 검색할 키워드 (빵집)
    
    var searchOption = {
        location: locPosition, // 내 위치 기준
        radius: 20000 // 검색 반경 (미터 단위)
    };

    // 키워드로 장소를 검색
    ps.keywordSearch(keyword, function(data, status, pagination) {
        if (status === kakao.maps.services.Status.OK) {
            for (var i = 0; i < data.length; i++) {
                displayMarker(map, data[i]);
            }
        } else {
            alert("주변 빵집을 찾을 수 없습니다.");
        }
    }, searchOption);
}

// 지도에 마커를 표시하는 함수
function displayMarker(map, place) {
    var marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x) // 빵집 위치
    });

    // 마커 클릭 시 장소 이름을 표시하는 인포윈도우
    var infowindow = new kakao.maps.InfoWindow({
        content: `<div style="padding:5px;font-size:12px;">${place.place_name}</div>`
    });

    kakao.maps.event.addListener(marker, 'click', function() {
        infowindow.open(map, marker);
    });
}*/
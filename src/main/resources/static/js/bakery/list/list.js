const locationAgree= document.getElementById('location-agree').value
   

var regionCenters = {
            서울: { lat: 37.5665, lng: 126.9780 },
            경기: { lat: 37.2750, lng: 127.0095 },
            인천: { lat: 37.4563, lng: 126.7052 },
            부산: { lat: 35.1796, lng: 129.0756 },
            대구: { lat: 35.8714, lng: 128.6014 },
            광주: { lat: 35.1595, lng: 126.8526 },
            대전: { lat: 36.3504, lng: 127.3845 },
            울산: { lat: 35.5384, lng: 129.3114 },
            세종: { lat: 36.4801, lng: 127.2890 },
            강원: { lat: 37.8854, lng: 127.7298 },
            충북: { lat: 36.6357, lng: 127.4912 },
            충남: { lat: 36.5184, lng: 126.8000 },
            전북: { lat: 35.7175, lng: 127.1530 },
            전남: { lat: 34.8679, lng: 126.9910 },
            경북: { lat: 36.5760, lng: 128.5056 },
            경남: { lat: 35.4606, lng: 128.2132 },
            제주: { lat: 33.4996, lng: 126.5312 }
        };
var mapContainer;
var mapOption ;
var map ;
var myPosition;
var locPosition;
var lng;
var lat;
var region;
var center;
var locPosition;

document.addEventListener("DOMContentLoaded", function () {
	mapContainer = document.getElementById('map'); 
	
	if(locationAgree === 'Y'){
		lat = 37.3987043; // 위도
		lng = 126.9207107; // 경도
	}else{
		lat = 37.5665;
		lng = 126.9780;
	}
	region = document.getElementById('searchText').value.split(" ")[0];
	myPosition = new kakao.maps.LatLng(lat,lng);
	center = regionCenters[region]; 
	console.log(mapContainer)
	
	if(center){
			locPosition = new kakao.maps.LatLng(center.lat,center.lng);
		} else {
			locPosition = myPosition;
		}
	
		console.log(locPosition)
		displayCurrentLocation(locPosition)
});
    // 지도에 현재 위치를 표시





// 현재 위치 마커 추가가
function displayCurrentLocation(locPosition) {
	

	
	
	
    // 지도를 표시할 div
	mapOption = { 
	        center: myPosition, // 현재 위치를 중심으로 지도 설정
	        level: 7 // 확대 레벨
	    };
	map = new kakao.maps.Map(mapContainer, mapOption);

	if(locationAgree === 'Y'){
		// 야구공모양 마커주소
			var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png';
			var imageSize = new kakao.maps.Size(30, 34); // 마커이미지의 크기
			var imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션.
			// 내위치 야구공모양 마커주소
			var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption)
		    // 내현재 위치를 표시할 마커 야구공 모양으로 생성
		    var marker = new kakao.maps.Marker({
		        map: map,
		        position: myPosition,
		        title: '내 위치',
				image : markerImage
		    });

		    // 내 현재 위치에 '내 위치'라는 인포윈도우 생성
		    var infowindow = new kakao.maps.InfoWindow({
		        content: '<div style="padding:5px;">내 위치</div>',
		        removable: true
		    });
	}
    
	
	
    infowindow.open(map, marker);
	map.setCenter(locPosition);
	$.ajax({
	               url: '/api/list',
	               type: "GET",
				   data:{
						searchText: $("#searchText").val(),
						orderType: $("#filter-select").val()
				   },
	               dataType: "json",
	               success: function (response) {
					console.log("?ASdasd")
					console.log(response)
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



function initializeSliders() {
    console.log('슬라이더 초기화 시작');
    const sliders = document.querySelectorAll('.slider');

    sliders.forEach((slider, index) => {
        const slides = slider.querySelector('.slides');
        const slideItems = slider.querySelectorAll('.hotel-slide');
        const prevBtn = slider.querySelector('.prev');
        const nextBtn = slider.querySelector('.next');
        const indicators = slider.querySelector('.slide-indicators');
        
        let currentIndex = 0;
        const totalSlides = slideItems.length;

        // 인디케이터 초기화 (중복 방지)
        indicators.innerHTML = ''; 
        for (let i = 0; i < totalSlides; i++) {
            const indicator = document.createElement('div');
            indicator.classList.add('indicator');
            if (i === 0) indicator.classList.add('active');
            indicator.addEventListener('click', () => {
                currentIndex = i;
                updateSlidePosition();
                updateIndicators();
            });
            indicators.appendChild(indicator);
        }

        // 슬라이드 위치 업데이트
        function updateSlidePosition() {
            slides.style.transform = `translateX(-${currentIndex * 100}%)`;
        }

        // 인디케이터 업데이트
        function updateIndicators() {
            const indicatorDots = indicators.querySelectorAll('.indicator');
            indicatorDots.forEach((dot, i) => {
                dot.classList.toggle('active', i === currentIndex);
            });
        }

        // 다음 슬라이드
        nextBtn.addEventListener('click', () => {
            currentIndex = (currentIndex + 1) % totalSlides;
            updateSlidePosition();
            updateIndicators();
        });

        // 이전 슬라이드
        prevBtn.addEventListener('click', () => {
            currentIndex = (currentIndex - 1 + totalSlides) % totalSlides;
            updateSlidePosition();
            updateIndicators();
        });

        // 자동 슬라이드 (5초마다)
        setInterval(() => {
            currentIndex = (currentIndex + 1) % totalSlides;
            updateSlidePosition();
            updateIndicators();
        }, 5000);
    });
}

// 초기 페이지 로딩 시 슬라이더 초기화
document.addEventListener('DOMContentLoaded', initializeSliders);

// ajax 페이징 처리
$(document).ready(function() {
    let currentPage = 2; // 초기 페이지 설정
    let isLoading = false; // 데이터 로딩 상태
	let orderType = $("#filter-select").val();
	console.log(currentPage)
    // 스크롤 이벤트 감지
    $(window).on('scroll', function() {
        // 스크롤이 페이지 하단에 도달했는지 확인
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            loadMorePosts(); // 추가 데이터 로드
        }
    });

    // Ajax를 통한 데이터 로드 함수
    function loadMorePosts() {
        if (isLoading) return; // 이미 로딩 중이면 중복 실행 방지
        isLoading = true; 
        console.log("Loading more posts...");
		console.log("현재페이지"+currentPage)
        $('#loading').show(); // 로딩 표시

        $.ajax({
            url: '/api/list',
            type: 'GET',
            data: {
				 currentPage: currentPage,
				 searchText: $("#searchText").val(),
				 orderType: $("#filter-select").val()
			 },
            dataType: 'json',
            success: function(response) {
                let posts = response.posts;
                let postContainer = $('.list-box');
				console.log(response)
				
				$(document).on('click', '.prev', function() {
				            const sliderId = $(this).data('slider');
				            console.log('이전 버튼 클릭, 슬라이더 ID:', sliderId);
				        });

				        $(document).on('click', '.next', function() {
				            const sliderId = $(this).data('slider');
				            console.log('다음 버튼 클릭, 슬라이더 ID:', sliderId);
				        });

                if (!posts || posts.length === 0) {
                    $(window).off("scroll"); // 더 이상 데이터가 없으면 스크롤 이벤트 제거
                    $('#loading').hide();
                    return;
                }

                // 포스트 데이터를 순회하여 HTML 생성 및 추가
                posts.forEach((post, index) => {
                    
					console.log(post.bakeryName)
					
					

                    let postHtml = `
                        <div class="list-item">
                            <div class="slider" id="slider${index + 11}">
                                <div class="slides">
								${post.bakeryImageDTO.map(image => `
                                    <div class="hotel-slide">
                                        <div class="image-container">
                                            <img src="${image.resourcesPath}/${image.changeName}" alt="빵 이미지 1" class="reserve-img">
                                            <p class="hotel-info2">${post.bakeryName}</p>
                                        </div>
                                    </div>
									`).join('')}
                                  </div>
                                <div class="direction-btn">
                                    <button class="prev" data-slider="${index + 11}">&lt;</button>
                                    <button class="next" data-slider="${index + 11}">&gt;</button>
                                </div>
                                <div class="slide-indicators" id="indicators${index + 11}"></div>
                            </div>
                            <div class="list-item-info">
                                <a href="/bakery/detail?bakeryNo=${post.bakeryNo}">
                                    <h2 class="list-item-title">${post.bakeryName}</h2>
                                </a>
                                <p class="list-item-address">📍<span>${post.bakeryAddress}</span></p>
                                <p class="list-item-score">⭐️평점: <span>${post.bakeryReviewDTO.reviewRating}</span></p>
                                <p class="list-item-time">🕒영업시간: <span>${post.bakeryScheduleDTO.bakeryOpenTime}</span>~<span>${post.bakeryScheduleDTO.bakeryCloseTime}</span></p>
                                <p class="list-item-review">📝리뷰: <span>${post.reviewCount}</span></p>
                                <p class="list-item-parking">🚗: <span>${post.bakeryDetailDTO.bakeryAmenity}</span></p>
                            </div>
                        </div>
                    `;

                    postContainer.append(postHtml);
                });

                currentPage++; // 다음 페이지를 위해 페이지 번호 증가
                isLoading = false; // 로딩 상태 초기화
                $('#loading').hide(); // 로딩 표시 숨김
				console.log("aaa")
				console.log(response)
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
									   
				initializeSliders(); // ajax 실행 후 슬라이드기능 삽입
            },
            error: function(xhr, status, error) {
                console.error("데이터 로드 중 오류 발생:", error);
                isLoading = false;
                $('#loading').hide();
            }
        });
    }
	$("#filter-select").change(function() {
	               orderType = $(this).val();
	               offset = 0;
	               loadMoreData(true);
	           });
	$("#searchBtn").click(function() {
	               offset = 0;
	               loadMoreData(true);
	           });
			   
			   
});








document.querySelector('.subscribe-button').addEventListener('click', function() {
    const emailInput = document.querySelector('.email-input');
    const subscribedMessage = document.getElementById('subscribed-message');
    
    if (emailInput.value && emailInput.value.includes('@')) {
        subscribedMessage.style.display = 'block';
        emailInput.value = '';
        setTimeout(() => {
            subscribedMessage.style.display = 'none';
        }, 3000);
    }
});

// 지역 버튼 클릭 시 활성화
const regionButtons = document.querySelectorAll('.region-button');

regionButtons.forEach(button => {
    button.addEventListener('click', function() {
        regionButtons.forEach(btn => btn.classList.remove('region-button-active'));
        this.classList.toggle('region-button-active');
		
		var region= this.getAttribute("data-region");
		
		fetchBakeries(region);
    });
});

// 네비게이션 메뉴 클릭 시 활성화
const navItems = document.querySelectorAll('.nav-item');
navItems.forEach(item => {
    item.addEventListener('click', function(e) {
        e.preventDefault();
        navItems.forEach(navItem => navItem.classList.remove('nav-item-active'));
        this.classList.add('nav-item-active');
    });
});
// 버튼 탭
const buttons = document.querySelectorAll(".region-button");
const maps = document.querySelectorAll(".korea-map");

buttons.forEach(button => {
    button.addEventListener("click", () => {
        const region = button.getAttribute("data-region");
		console.log(region);
        buttons.forEach(btn => btn.classList.remove("region-button-active"));
        button.classList.add("region-button-active");

        maps.forEach(map => {
            if (map.getAttribute("data-region") === region) {
                map.classList.add("active");
            } else {
                map.classList.remove("active");
            }
        });
    });
});

// ✅ Kakao 지도 초기화 및 지역별 중심 좌표 설정
		var map;
		var markers = [];
		
        // ✅ 지역별 중심 좌표 설정
        var regionCenters = {
            seoul: { lat: 37.5665, lng: 126.9780 },
            gyeonggi: { lat: 37.2750, lng: 127.0095 },
            incheon: { lat: 37.4563, lng: 126.7052 },
            busan: { lat: 35.1796, lng: 129.0756 },
            daegu: { lat: 35.8714, lng: 128.6014 },
            gwangju: { lat: 35.1595, lng: 126.8526 },
            daejeon: { lat: 36.3504, lng: 127.3845 },
            ulsan: { lat: 35.5384, lng: 129.3114 },
            sejong: { lat: 36.4801, lng: 127.2890 },
            gangwon: { lat: 37.8854, lng: 127.7298 },
            chungbuk: { lat: 36.6357, lng: 127.4912 },
            chungnam: { lat: 36.5184, lng: 126.8000 },
            jeonbuk: { lat: 35.7175, lng: 127.1530 },
            jeonnam: { lat: 34.8679, lng: 126.9910 },
            gyeongbuk: { lat: 36.5760, lng: 128.5056 },
            gyeongnam: { lat: 35.4606, lng: 128.2132 },
            jeju: { lat: 33.4996, lng: 126.5312 }
        };

		// ✅ Kakao 지도 초기화 함수
		function initMap() {
			
			
		    var mapContainer = document.getElementById('map');
		    var mapOptions = {
		        center: new kakao.maps.LatLng(regionCenters.seoul.lat, regionCenters.seoul.lng),
		        level: 9
		    };
		    map = new kakao.maps.Map(mapContainer, mapOptions);
			
			
		}

		// ✅ 지도 업데이트 (마커 추가 + 지도 이동)
		function updateMap(region, bakeries) {
			console.log("데이터",bakeries)
		    // ✅ 기존 마커 제거
		    markers.forEach(marker => marker.setMap(null));
		    markers = [];

		    // ✅ 지도 중심 이동
		    var center = regionCenters[region];
		    if (center) {
		        map.setCenter(new kakao.maps.LatLng(center.lat, center.lng));
		        map.setLevel(7);
		    }
			let addedPlaces = new Set();

		    // ✅ 새로운 마커 추가
		    bakeries.forEach(bakery => {
		        var position = new kakao.maps.LatLng(bakery.latitude, bakery.longitude);
				
				if (addedPlaces.has(bakery.name)) return;
				        addedPlaces.add(bakery.name);
						
		        var marker = new kakao.maps.Marker({ position: position, map: map });

		        var infoWindow = new kakao.maps.InfoWindow({ content: `<div style="padding:5px;">${bakery.name}</div>` });
		        kakao.maps.event.addListener(marker, 'click', function() {
		            infoWindow.open(map, marker);
		        });

		        markers.push(marker);
		    });
		}

		// ✅ Kakao 지도 API가 로드된 후 `initMap()` 실행
		if (window.kakao && window.kakao.maps) {
		    initMap();
		} else {
		    document.addEventListener("DOMContentLoaded", function() {
		        kakao.maps.load(initMap);
		    });
		}



        document.addEventListener("DOMContentLoaded", function () {
            var swiper = new Swiper(".swiper-popularBakerySwiper", {
                slidesPerView: 3, // 한 번에 보여줄 카드 개수
                spaceBetween: 20, // 카드 간격
                loop: true, // 무한 루프
                navigation: {
                    nextEl: ".swiper-button-next",
                    prevEl: ".swiper-button-prev",
                },
                pagination: {
                    el: ".swiper-pagination",
                    clickable: true,
                },
                breakpoints: {
                    1024: { slidesPerView: 3 },
                    768: { slidesPerView: 2 },
                    480: { slidesPerView: 1 }
                }
            });
        });

        document.addEventListener("DOMContentLoaded", function () {
            var recentBakerySwiper = new Swiper(".recentBakerySwiper", {
                slidesPerView: 3, // 한 번에 보여줄 카드 개수
                spaceBetween: 20, // 카드 간격
                loop: true, // 무한 루프
                navigation: {
                    nextEl: ".recent-next",
                    prevEl: ".recent-prev",
                },
                pagination: {
                    el: ".recent-pagination",
                    clickable: true,
                },
                breakpoints: {
                    1024: { slidesPerView: 3 },
                    768: { slidesPerView: 2 },
                    480: { slidesPerView: 1 }
                }
            });
        });




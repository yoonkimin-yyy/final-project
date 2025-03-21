document.querySelector('.subscribe-button').addEventListener('click', function () {
	const emailInput = document.querySelector('.email-input');
	const subscribedMessage = document.getElementById('subscribed-message');

	console.log(subscribedMessage);
	
	
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
	button.addEventListener('click', function () {
		regionButtons.forEach(btn => btn.classList.remove('region-button-active'));
		this.classList.toggle('region-button-active');

		var region = this.getAttribute("data-region");

		fetchBakeriesByRegion(region);
	});
});

// 네비게이션 메뉴 클릭 시 활성화
const navItems = document.querySelectorAll('.nav-item');
navItems.forEach(item => {
	item.addEventListener('click', function (e) {
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




// ✅ 지역별 중심 좌표 (한글 기준)
const regionCenters = {
    "서울": { lat: 37.5665, lng: 126.9780 },
    "경기": { lat: 37.2750, lng: 127.0095 },
    "인천": { lat: 37.4563, lng: 126.7052 },
    "부산": { lat: 35.1796, lng: 129.0756 },
    "대구": { lat: 35.8714, lng: 128.6014 },
    "광주": { lat: 35.1595, lng: 126.8526 },
    "대전": { lat: 36.3504, lng: 127.3845 },
    "울산": { lat: 35.5384, lng: 129.3114 },
    "세종": { lat: 36.4801, lng: 127.2890 },
    "강원": { lat: 37.8854, lng: 127.7298 },
    "충북": { lat: 36.6357, lng: 127.4912 },
    "충남": { lat: 36.5184, lng: 126.8000 },
    "전북": { lat: 35.7175, lng: 127.1530 },
    "전남": { lat: 34.8679, lng: 126.9910 },
    "경북": { lat: 36.5760, lng: 128.5056 },
    "경남": { lat: 35.4606, lng: 128.2132 },
    "제주": { lat: 33.4996, lng: 126.5312 }
};

let map;
let markers = [];

// ✅ Kakao 지도 초기화
function initMap() {
    const mapContainer = document.getElementById('map');
    const mapOptions = {
        center: new kakao.maps.LatLng(regionCenters["서울"].lat, regionCenters["서울"].lng),
        level: 9
    };
    map = new kakao.maps.Map(mapContainer, mapOptions);
}

// ✅ 지도 중심 이동
function moveToRegion(region) {
    const center = regionCenters[region];
    if (center) {
        map.setCenter(new kakao.maps.LatLng(center.lat, center.lng));
        map.setLevel(7);
    } else {
        console.warn("중심 좌표 없음:", region);
    }
}


// ✅ 지도 마커 업데이트
function updateMap(region, bakeries) {
    markers.forEach(marker => marker.setMap(null));
    markers = [];

    const center = regionCenters[region];
    if (center) {
        map.setCenter(new kakao.maps.LatLng(center.lat, center.lng));
        map.setLevel(10);
    }

    const addedPlaces = new Set();

    bakeries.forEach(bakery => {
        if (addedPlaces.has(bakery.name)) return;
        addedPlaces.add(bakery.name);

        const position = new kakao.maps.LatLng(bakery.latitude, bakery.longitude);
        const marker = new kakao.maps.Marker({
            position: position,
            map: map,
            image: new kakao.maps.MarkerImage(
                "/img/common/bread.png",
                new kakao.maps.Size(30, 30),
                { offset: new kakao.maps.Point(20, 40) }
            )
        });

        const content = `
            <div style="padding:10px; width: 250px; font-size: 14px;">
                <strong style="font-size: 16px;">🍞 ${bakery.name}</strong><br>
                📍 <span>${bakery.address}</span><br>
                <button onclick="viewDetails('${bakery.name}')" 
                    style="margin-top: 5px; padding: 5px; border: none; background: #ffcc00; cursor: pointer;">
                    상세보기
                </button>
            </div>
        `;

        const infoWindow = new kakao.maps.InfoWindow({ content: content });
        kakao.maps.event.addListener(marker, 'click', () => {
            infoWindow.open(map, marker);
        });

        markers.push(marker);
    });
}

// ✅ 버튼 클릭 이벤트 (한 번만 등록)
document.querySelectorAll(".region-button").forEach(button => {
    button.addEventListener("click", () => {
        const region = button.getAttribute("data-region");

        document.querySelectorAll(".region-button").forEach(btn => btn.classList.remove("region-button-active"));
        button.classList.add("region-button-active");

        moveToRegion(region);
        fetchBakeriesByRegion(region);
    });
});

// ✅ 기본값: 서울
document.addEventListener("DOMContentLoaded", function () {
    initMap();
    moveToRegion("서울");
    fetchBakeriesByRegion("서울");
});

// ✅ 지역 버튼에 개수 표시 (옵션)
function updateRegionButton(region, count, unit) {
    const button = document.querySelector(`[data-region="${region}"]`);
    if (button) {
        let countSpan = button.querySelector(".count");
        if (!countSpan) {
            countSpan = document.createElement("span");
            countSpan.classList.add("count");
            button.appendChild(countSpan);
        }
        countSpan.textContent = `${count}${unit}`;
    }
}

// ✅ 상세보기 클릭 시 이동 (예시)
function viewDetails(bakeryName) {
    window.location.href = `/bbanggil/bakery/detail?name=${encodeURIComponent(bakeryName)}`;
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

// 인기 빵집
function updatePopularBakeries(data) {

	let swiperWrapper = document.querySelector('.swiper-popularBakerySwiper .swiper-wrapper');
	swiperWrapper.innerHTML = "";

	data.forEach(bakery => {

		let slide = document.createElement("div");

		slide.classList.add("swiper-slide", "bakery-card");
		slide.innerHTML = `
				<img src="${bakery.response ? bakery.response.resourcesPath + '/' + bakery.response.changeName : '/images/default.jpg'}"> 
		            <div class="bakery-info">
		                <h3 class="bakery-name">${bakery.name}</h3>
		                <div class="location-container">
		                    <i class="fas fa-map-marker-alt"></i>
		                    <span class="location-text">${bakery.address}</span>
		                </div>
		                <div class="rating-container">
		                    <i class="fas fa-star"></i>
		                    <span class="rating-text">${bakery.response ? bakery.response.reviewRating.toFixed(1) : '평점 없음'}</span>
		                </div>
		                <div class="specialty-container">
		                    <span class="specialty-label">${bakery.response.categoryName}</span>
		                    <span class="specialty-text">${bakery.response.menuName}</span>
		                </div>
		            </div>
		        `;
		swiperWrapper.appendChild(slide);
	});
}

// 신규 오픈 빵집
function updateRecentBakeries(data) {
	let swiperWrapper = document.querySelector('.swiper-recentBakerySwiper .swiper-wrapper');
	swiperWrapper.innerHTML = "";

	data.forEach(bakery => {
		let slide = document.createElement("div");

		slide.classList.add("swiper-slide", "bakery-card");
		slide.innerHTML = `
						<img src="${bakery.response ? bakery.response.resourcesPath + '/' + bakery.response.changeName : '/images/default.jpg'}"> 
				            <div class="bakery-info">
				                <h3 class="bakery-name">${bakery.name}</h3>
				                <div class="location-container">
				                    <i class="fas fa-map-marker-alt"></i>
				                    <span class="location-text">${bakery.address}</span>
				                </div>
				                <div class="open-date-container">
				                    <i class="fas fa-calendar-alt"></i>
				                    <span class="open-date-text">${bakery.response.createDate}</span>
				                </div>
				                <div class="specialty-container">
				                    <span class="specialty-label">${bakery.response.categoryName}</span>
				                    <span class="specialty-text">${bakery.response.menuName}</span>
				                </div>
				            </div>
				        `;
		swiperWrapper.appendChild(slide);
	});

}
document.addEventListener("DOMContentLoaded", function () {
	var swiper = new Swiper(".swiper-popularBakerySwiper", {
		slidesPerView: 3,
		spaceBetween: 10,
		loop: true,
		navigation: {
			nextEl: ".swiper-button-next",
			prevEl: ".swiper-button-prev",
		},
		pagination: {
			el: ".swiper-pagination",
			clickable: true,
		},
		autoplay: {        // 자동 재생
			delay: 3000,
			disableOnInteraction: false,
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
		spaceBetween: 10, // 카드 간격
		loop: true, // 무한 루프
		navigation: {
			nextEl: ".recent-next",
			prevEl: ".recent-prev",
		},
		pagination: {
			el: ".recent-pagination",
			clickable: true,
		},
		autoplay: { // 자동 재생 추가
			delay: 3000,
			disableOnInteraction: false,
		},
		breakpoints: {
			1024: { slidesPerView: 3 },
			768: { slidesPerView: 2 },
			480: { slidesPerView: 1 }
		}
	});
});

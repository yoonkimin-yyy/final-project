document.querySelector('.subscribe-button').addEventListener('click', function () {
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
	button.addEventListener('click', function () {
		regionButtons.forEach(btn => btn.classList.remove('region-button-active'));
		this.classList.toggle('region-button-active');

		var region = this.getAttribute("data-region");

		fetchBakeries(region);
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

//  한글 → 영어 매핑
const regionMap = {
	"서울": "seoul",
	"경기": "gyeonggi",
	"인천": "incheon",
	"부산": "busan",
	"대구": "daegu",
	"광주": "gwangju",
	"대전": "daejeon",
	"울산": "ulsan",
	"세종": "sejong",
	"강원": "gangwon",
	"충북": "chungbuk",
	"충남": "chungnam",
	"전북": "jeonbuk",
	"전남": "jeonnam",
	"경북": "gyeongbuk",
	"경남": "gyeongnam",
	"제주": "jeju"
};


const reverseRegionMap = {};
for (let key in regionMap) {
	reverseRegionMap[regionMap[key]] = key;
}


//  Kakao 지도 초기화 및 지역별 중심 좌표 설정
var map;
var markers = [];

//  지역별 중심 좌표 설정
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


function moveToRegion(region) {

	let englishRegion = regionMap[region];

	var center = regionCenters[englishRegion];


	if (center) {
		map.setCenter(new kakao.maps.LatLng(center.lat, center.lng)); // ✅ 지도 중심 이동
		map.setLevel(8);  //  지도 줌 레벨 설정 (7은 도시 수준)
	} else {
		console.warn("해당 지역의 중심 좌표가 없습니다.");
	}
}


//  기본적으로 서울 지역 데이터 로드
document.addEventListener("DOMContentLoaded", function () {

	moveToRegion("서울"); //  기본값 한글로 설정
});


//  지역 버튼 클릭 이벤트 추가
document.querySelectorAll(".region-button").forEach(button => {
	button.addEventListener("click", function () {
		var region = this.getAttribute("data-region");

		//  버튼 활성화 효과
		document.querySelectorAll(".region-button").forEach(btn => btn.classList.remove("region-button-active"));
		this.classList.add("region-button-active");
		moveToRegion(region);
	});
});

//  Kakao 지도 초기화 함수
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

		var imageSrc = "/img/common/bread.png"
		var imageSize = new kakao.maps.Size(30, 30);
		var imageOption = { offset: new kakao.maps.Point(20, 40) };
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

		if (addedPlaces.has(bakery.name)) return;
		addedPlaces.add(bakery.name);

		var marker = new kakao.maps.Marker({ position: position, map: map, image: markerImage });

		var content = `
				       <div style="padding:10px; width: 250px; font-size: 14px;">
				           <strong style="font-size: 16px;">🍞 ${bakery.name}</strong><br>
				           📍 <span>${bakery.address}</span><br>
				           <button onclick="viewDetails('${bakery.name}')" 
				               style="margin-top: 5px; padding: 5px; border: none; background: #ffcc00; cursor: pointer;">
				               상세보기
				           </button>
				       </div>
				   `;

		var infoWindow = new kakao.maps.InfoWindow({ content: content });
		kakao.maps.event.addListener(marker, 'click', function () {
			infoWindow.open(map, marker);
		});

		markers.push(marker);
	});
}

//  Kakao 지도 API가 로드된 후 `initMap()` 실행
if (window.kakao && window.kakao.maps) {
	initMap();
} else {
	document.addEventListener("DOMContentLoaded", function () {
		kakao.maps.load(initMap);
	});
}



// 인기 빵집
function updatePopularBakeries(data) {

	let swiperWrapper = document.querySelector('.swiper-popularBakerySwiper .swiper-wrapper');
	swiperWrapper.innerHTML = "";

	data.forEach(bakery => {

		let slide = document.createElement("div");

		slide.classList.add("swiper-slide", "bakery-card");
		slide.innerHTML = `
				<img src="${bakery.image ? bakery.image.resourcesPath + '/' + bakery.image.changeName : '/images/default.jpg'}"> 
		            <div class="bakery-info">
		                <h3 class="bakery-name">${bakery.name}</h3>
		                <div class="location-container">
		                    <i class="fas fa-map-marker-alt"></i>
		                    <span class="location-text">${bakery.address}</span>
		                </div>
		                <div class="rating-container">
		                    <i class="fas fa-star"></i>
		                    <span class="rating-text">${bakery.review ? bakery.review.reviewRating.toFixed(1) : '평점 없음'}</span>
		                </div>
		                <div class="specialty-container">
		                    <span class="specialty-label">${bakery.menu.categoryName}</span>
		                    <span class="specialty-text">${bakery.menu.menuName}</span>
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
						<img src="${bakery.image ? bakery.image.resourcesPath + '/' + bakery.image.changeName : '/images/default.jpg'}"> 
				            <div class="bakery-info">
				                <h3 class="bakery-name">${bakery.name}</h3>
				                <div class="location-container">
				                    <i class="fas fa-map-marker-alt"></i>
				                    <span class="location-text">${bakery.address}</span>
				                </div>
				                <div class="open-date-container">
				                    <i class="fas fa-calendar-alt"></i>
				                    <span class="open-date-text">${bakery.detail.createDate}</span>
				                </div>
				                <div class="specialty-container">
				                    <span class="specialty-label">${bakery.menu.categoryName}</span>
				                    <span class="specialty-text">${bakery.menu.menuName}</span>
				                </div>
				            </div>
				        `;
		swiperWrapper.appendChild(slide);
	});

}

document.addEventListener("DOMContentLoaded", function () {
	var swiper = new Swiper(".swiper-popularBakerySwiper", {
		slidesPerView: 3,
		spaceBetween: 20,
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

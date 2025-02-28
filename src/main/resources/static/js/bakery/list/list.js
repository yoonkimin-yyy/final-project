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
        $('#loading').show(); // 로딩 표시

        $.ajax({
            url: '/bakery/api/list',
            type: 'GET',
            data: { currentPage: currentPage },
            dataType: 'json',
            success: function(response) {
                console.log(response);
                let posts = response.posts;
                let postContainer = $('.list-box');
				
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
                    console.log("Adding post:", post);

                    let postHtml = `
                        <div class="list-item">
                            <div class="slider" id="slider${index + 11}">
                                <div class="slides">
                                    <div class="hotel-slide">
                                        <div class="image-container">
                                            <img src="https://api.a0.dev/assets/image?text=delicious%20bread%201&aspect=16:9" alt="빵 이미지 1" class="reserve-img">
                                            <p class="hotel-info2">크로와상</p>
                                            <p class="bread-price">4,500원</p>
                                        </div>
                                    </div>
                                    <div class="hotel-slide">
                                        <div class="image-container">
                                            <img src="https://api.a0.dev/assets/image?text=delicious%20bread%202&aspect=16:9" alt="빵 이미지 2" class="reserve-img">
                                            <p class="hotel-info2">바게트</p>
                                            <p class="bread-price">3,800원</p>
                                        </div>
                                    </div>
                                    <div class="hotel-slide">
                                        <div class="image-container">
                                            <img src="https://api.a0.dev/assets/image?text=delicious%20bread%203&aspect=16:9" alt="빵 이미지 3" class="reserve-img">
                                            <p class="hotel-info2">식빵</p>
                                            <p class="bread-price">5,000원</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="navigation">
                                    <button class="prev" data-slider="${index + 11}">&lt;</button>
                                    <button class="next" data-slider="${index + 11}">&gt;</button>
                                </div>
                                <div class="slide-indicators" id="indicators${index + 11}"></div>
                            </div>
                            <div class="list-item-info">
                                <a href="">
                                    <h2 class="list-item-title">${post.bakeryName}</h2>
                                </a>
                                <p class="list-item-address">📍<span>${post.bakeryAddress}</span></p>
                                <p class="list-item-score">⭐️평점: <span>${post.bakeryReviewDTO.reviewRating}</span></p>
                                <p class="list-item-time">🕒영업시간: <span>${post.bakeryScheduleDTO.bakeryOpenTime}</span>~<span>${post.bakeryScheduleDTO.bakeryCloseTime}</span></p>
                                <p class="list-item-review">📝리뷰: <span></span></p>
                                <p class="list-item-parking">🚗: <span>${post.bakeryAmenity}</span></p>
                            </div>
                        </div>
                    `;

                    postContainer.append(postHtml);
                });

                currentPage++; // 다음 페이지를 위해 페이지 번호 증가
                isLoading = false; // 로딩 상태 초기화
                $('#loading').hide(); // 로딩 표시 숨김
				initializeSliders(); // ajax 실행 후 슬라이드기능 삽입
            },
            error: function(xhr, status, error) {
                console.error("데이터 로드 중 오류 발생:", error);
                isLoading = false;
                $('#loading').hide();
            }
        });
    }
});










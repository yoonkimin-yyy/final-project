document.addEventListener('DOMContentLoaded', function() {
    // 모든 슬라이더 초기화
    const sliders = document.querySelectorAll('.slider');
    
    sliders.forEach((slider, index) => {
        const sliderId = `slider${index + 1}`;
        const slides = slider.querySelector('.slides');
        const slideItems = slider.querySelectorAll('.hotel-slide');
        const prevBtn = slider.querySelector('.prev');
        const nextBtn = slider.querySelector('.next');
        const indicators = document.getElementById(`indicators${index + 1}`);
        
        let currentIndex = 0;
        const totalSlides = slideItems.length;

        // 인디케이터 생성
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

        // 자동 슬라이드 (5초마다)
        setInterval(() => {
            currentIndex = (currentIndex + 1) % totalSlides;
            updateSlidePosition();
            updateIndicators();
        }, 5000);

        // 터치/드래그 이벤트
        let startX;
        let isDragging = false;

        slides.addEventListener('mousedown', startDragging);
        slides.addEventListener('touchstart', startDragging);
        slides.addEventListener('mousemove', drag);
        slides.addEventListener('touchmove', drag);
        slides.addEventListener('mouseup', endDragging);
        slides.addEventListener('touchend', endDragging);
        slides.addEventListener('mouseleave', endDragging);

        function startDragging(e) {
            isDragging = true;
            startX = e.type === 'mousedown' ? e.pageX : e.touches[0].pageX;
        }

        function drag(e) {
            if (!isDragging) return;
            e.preventDefault();
            const x = e.type === 'mousemove' ? e.pageX : e.touches[0].pageX;
            const walk = x - startX;
            
            if (Math.abs(walk) > 100) {
                if (walk > 0 && currentIndex > 0) {
                    currentIndex--;
                } else if (walk < 0 && currentIndex < totalSlides - 1) {
                    currentIndex++;
                }
                updateSlidePosition();
                updateIndicators();
                isDragging = false;
            }
        }

        function endDragging() {
            isDragging = false;
        }
    });
});


function handleAction(action) {
	document.getElementById("actionInput").value = action;
	
    if (action === '거절') {
        const rejectReason = document.getElementById('reason').value;
        if (!rejectReason) {
            alert("거절 사유를 입력해야 합니다.");
			event.preventDefault();
        	return false;
        }
    }

    document.getElementById("bakeryForm").submit();
}

function goBack() {
	event.preventDefault();
	window.history.back();
}

// 모달 요소
var modal = document.getElementById("myModal");
// 모달 열기 버튼
var openModalBtn = document.getElementById("openModalBtn");
// 모달 닫기 버튼
var closeBtn = document.getElementById("closeBtn");

// 모달 열기
openModalBtn.onclick = function() {
	event.preventDefault();
    modal.style.display = "block";
}

// 모달 닫기
closeBtn.onclick = function() {
	event.preventDefault();
    modal.style.display = "none";
}

// 모달 외부 클릭 시 닫기
window.onclick = function(event) {
	event.preventDefault();
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

let currentIndex = 0;

function moveSlide(step) {
    const slides = document.querySelectorAll('.slide');
    const totalSlides = slides.length;

    // 현재 인덱스를 변경하여 슬라이드 이동
    currentIndex += step;

    // 인덱스가 범위를 벗어나지 않도록 처리
    if (currentIndex >= totalSlides) {
        currentIndex = 0; // 마지막에서 다음을 눌렀을 때 첫 번째로 돌아감
    }
    if (currentIndex < 0) {
        currentIndex = totalSlides - 1; // 첫 번째에서 이전을 눌렀을 때 마지막으로 돌아감
    }

    // 슬라이드 이동
    const sliderContainer = document.querySelector('.slider-container');
    const slideWidth = slides[0].offsetWidth;
    sliderContainer.style.transform = `translateX(-${currentIndex * slideWidth}px)`;
}

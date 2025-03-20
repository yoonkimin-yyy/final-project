let warningCount=0;
function handleWarning() {
    
    warningCount++;

    if (warningCount === 1) {
        alert('경고 조치 되었습니다');
    } else if (warningCount === 2) {
        alert('3일 정지 되었습니다');
    } else if (warningCount === 3) {
        alert('7일 정지 되었습니다');
    }
    closePopup();
}

function handleBan(userId) {
    alert('계정이 정지되었습니다');
    closePopup();
}
function openPopup() {
    window.open('popup.html', 'popup', 'width=510, height=415, left=900, top=300',scrollbars='no');
}
function closePopup() {
    document.getElementById('popup').style.display = 'none';
    history.pushState(null, null, ' ');
}
function userDetail(){
    window.open("/admin/user/detail");
}



function showTab(tabId) {
    var tabs = document.getElementsByClassName('tab-content');
    for (var i = 0; i < tabs.length; i++) {
        tabs[i].classList.remove('active-content');
    }
    document.getElementById(tabId).classList.add('active-content');

    var tabButtons = document.getElementsByClassName('tab');
    for (var i = 0; i < tabButtons.length; i++) {
        tabButtons[i].classList.remove('active');
    }
    event.target.classList.add('active');
}

const ctx = document.getElementById('orderChart').getContext('2d');
new Chart(ctx, {
    type: 'line',
    data: {
        labels: ['1월', '2월', '3월', '4월', '5월', '6월'],
        datasets: [{
            label: '월별 주문량',
            data: [650, 590, 880, 810, 1200, 1100],
            fill: false,
            borderColor: '#3498db',
            tension: 0.1
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            title: {
                display: false
            }
        },
        scales: {
            y: {
                beginAtZero: true,
                title: {
                    display: true,
                    text: '주문 건수'
                }
            }
        }
    }
});
function bakeryDetail(){
    window.open("/admin/bakery/detail");
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
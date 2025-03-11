document.addEventListener("DOMContentLoaded", function () {

    // ===== 탭 전환 기능 =====
    const tabButtons = document.querySelectorAll('.tab-button');
    tabButtons.forEach(button => {
        button.addEventListener('click', () => {

            tabButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
            const tab = button.dataset.tab;
            const allTabs = ['menu', 'reviews', 'interior', 'exterior', 'parking'];
            allTabs.forEach(tabId => {
                const tabElement = document.getElementById(tabId);
                if (tabElement) {
                    tabElement.style.display = tab === tabId ? 'block' : 'none';
                }
            });
        });
    });

    // ===== 영업시간 토글 기능 =====
    const hoursToggle = document.querySelector('.hours-toggle');
    const hoursDetail = document.querySelector('.hours-detail');
    const daysTable = document.getElementById('days-table');
    const dateTable = document.getElementById('date-table');
    const daySelect = document.getElementById('choose-day');
    const dateSelect = document.getElementById('choose-date');
    let isExpanded = false;


    // 오늘의 영업시간 표시
    const days = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
    const today = new Date().getDay();
    const todayHours = document.getElementById('today-hours');
    const timeString = today === 0 || today === 6 ? '09:00 - 20:00' : '07:00 - 22:00';
    todayHours.textContent = `영업시간 선택(더보기 클릭)`;

    hoursToggle.addEventListener('click', () => {

        isExpanded = !isExpanded;
        hoursDetail.style.display = isExpanded ? 'block' : 'none';
        hoursToggle.textContent = isExpanded ? '접기' : '더보기';
    });
    daySelect.addEventListener('change', () => {
        if (daySelect.checked) {
            dateSelect.checked = false;
            dateTable.style.display = 'none';
            daysTable.style.display = 'block';
        }
    });
    dateSelect.addEventListener('change', () => {
        if (dateSelect.checked) {
            daySelect.checked = false;
            dateTable.style.display = 'block';
            daysTable.style.display = 'none';
        }
    });

});

function openUpdateMenu() {
    window.open("/bakery/menu/insert/form", "_blank", "width=600, height=400, top=100, left=100");
}

function previewImages(event, previewId) {
            const previewContainer = document.getElementById(previewId);
            previewContainer.innerHTML = ''; // 기존 이미지 초기화
            const files = event.target.files;

            for (const file of files) {
                const reader = new FileReader();
                reader.onload = function (e) {
                    const img = document.createElement('img');
                    img.src = e.target.result;
                    img.style.width = '150px';
                    img.style.margin = '5px';
                    previewContainer.appendChild(img);
                };
                reader.readAsDataURL(file);
            }
        }




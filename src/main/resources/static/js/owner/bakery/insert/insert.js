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
	
		const fileInputs = document.querySelectorAll("input[type=file]");
        const previewMap = {}; // 각 input 별 이미지 리스트 저장

        fileInputs.forEach(input => {
            const previewContainerId = input.getAttribute("data-preview-container");
            const previewContainer = document.getElementById(previewContainerId);
            const previewImage = previewContainer.querySelector(".preview-img");
            const prevBtn = previewContainer.querySelector(".prev-btn");
            const nextBtn = previewContainer.querySelector(".next-btn");

            previewMap[previewContainerId] = { images: [], currentIndex: 0 };

            input.addEventListener("change", function (event) {
                handleFileChange(event.target, previewContainerId);
            });

            prevBtn.addEventListener("click", () => changeImage(previewContainerId, -1));
            nextBtn.addEventListener("click", () => changeImage(previewContainerId, 1));
           
        });

        function handleFileChange(inputElement, previewContainerId) {
            const files = inputElement.files;
            if (files.length === 0) return;

            const previewContainer = document.getElementById(previewContainerId);
            const previewImage = previewContainer.querySelector(".preview-img");
            const prevBtn = previewContainer.querySelector(".prev-btn");
            const nextBtn = previewContainer.querySelector(".next-btn");

            previewMap[previewContainerId].images = [];
            previewMap[previewContainerId].currentIndex = 0;

            Array.from(files).forEach(file => {
                const reader = new FileReader();
                reader.onload = function (e) {
                    previewMap[previewContainerId].images.push(e.target.result);
                    if (previewMap[previewContainerId].images.length === 1) {
                        previewImage.src = e.target.result;
                        previewImage.style.display = "block";
                        updateButtons(previewContainerId);
                    }
                };
                reader.readAsDataURL(file);
            });
        }

        function changeImage(previewContainerId, direction) {
            const previewContainer = document.getElementById(previewContainerId);
            const previewImage = previewContainer.querySelector(".preview-img");
            let data = previewMap[previewContainerId];

            if (!data.images.length) return;

            data.currentIndex += direction;
            if (data.currentIndex < 0) data.currentIndex = 0;
            if (data.currentIndex >= data.images.length) data.currentIndex = data.images.length - 1;

            previewImage.src = data.images[data.currentIndex];
            updateButtons(previewContainerId);
        }

        function updateButtons(previewContainerId) {
            const previewContainer = document.getElementById(previewContainerId);
            const prevBtn = previewContainer.querySelector(".prev-btn");
            const nextBtn = previewContainer.querySelector(".next-btn");
            let data = previewMap[previewContainerId];
			console.log(data.currentIndex);

            /*prevBtn.style.display = data.currentIndex > 0 ? "block" : "none";
            nextBtn.style.display = data.currentIndex < data.images.length - 1 ? "block" : "none";*/
        }

});

function openUpdateMenu() {
    window.open("/bakery/menu/insert/form", "_blank", "width=600, height=400, top=100, left=100");
}


		   

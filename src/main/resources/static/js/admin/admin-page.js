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

/*const ctx = document.getElementById('orderChart').getContext('2d');
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
});*/
function bakeryDetail(){
    window.open("/admin/bakery/detail");
}
/*
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
*/
// 모달 요소
var modal = document.getElementById("myModal");
// 모달 열기 버튼
var openModalBtn = document.getElementById("openModalBtn");
// 모달 닫기 버튼
var closeBtn = document.getElementById("closeBtn");

let selectedEmails = [];  // 선택된 이메일을 저장할 배열

// 모달 열기
openModalBtn.onclick = function() {
	//event.preventDefault();
    modal.style.display = "block";
}

// 체크박스를 클릭할 때 이메일을 모달에 추가하거나 제거
function addEmailToModal(event, checkbox) {
    const email = checkbox.getAttribute('data-email');
    // 체크된 경우 이메일 배열에 추가
    if (checkbox.checked) {
        selectedEmails.push(email);
    } else {
        // 체크 해제된 경우 이메일 배열에서 제거
        selectedEmails = selectedEmails.filter(e => e !== email);
    }
	// 모달에 이메일 목록 업데이트
	updateEmailField();
}

function updateEmailField() {
    const emailField = document.getElementById('email');
    
    // 이메일 필드에 선택된 이메일들을 콤마로 구분하여 표시
    emailField.value = selectedEmails.join(', ');
}

// 모달 닫기
closeBtn.onclick = function() {
	//event.preventDefault();
    modal.style.display = "none";
}

cancelBtn.onclick = function() {
	//event.preventDefault();
    modal.style.display = "none";
}

// 모달 외부 클릭 시 닫기
window.onclick = function(event) {
	//event.preventDefault();
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

document.getElementById('submitBtn').addEventListener('click', function(event) {
    event.preventDefault();  // 폼 제출 방지 (기본 동작 막기)
	
    // 이메일 전송 로직 (예: AJAX 요청 보내기)
    const email = document.getElementById('email').value;
    const subject = document.getElementById('subject').value;
    const message = document.getElementById('message').value;
    console.log('이메일1:', email);
	console.log('이메일2:', selectedEmails);
    console.log('제목:', subject);
    console.log('메시지:', message);
	
	var emailInfo = {
		address: email,
		title: subject,
		content: message
	};
	
	$.ajax({
		type: 'POST',
		url: '/api/admin/sendEmail',
		data: JSON.stringify(emailInfo),
		contentType: "application/json"
	}).then(function (res) {
		if(res) {
			alert('보내기성공!');
			modal.style.display = "none";
		}
	}).catch(function (err) {
		alert('실패!');
	});

    // 추가적인 이메일 전송 코드 작성...
}); //document.getElementById('submitBtn').addEventListener('click', function(event) {

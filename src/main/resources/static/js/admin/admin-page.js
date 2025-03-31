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

fetch('/api/admin/monthly-count')
    .then(response => response.json())
    .then(data => {
        const labels = data.map(item => item.orderMonth);
        const values = data.map(item => item.orderCount);
			const ctx = document.getElementById('orderChart').getContext('2d');
			new Chart(ctx, {
			    type: 'line',
			    data: {
			        labels: labels,
			        datasets: [{
			            label: '월별 주문량',
			            data: values,
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
		})
		.catch(error => console.error("Error fetching chart data:", error));
			
function bakeryDetail(){
    window.open("/admin/bakery/detail");
}

// 전체선택 버튼 클릭 시 체크박스를 모두 선택하거나 해제
document.getElementById("email-btn").addEventListener("click", function() { 
    const checkboxes = document.querySelectorAll("input[name='check1']");
    const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);
    
    checkboxes.forEach(checkbox => {
        checkbox.checked = !allChecked;  // 선택 상태를 반대로 설정
        addEmailToModal(null, checkbox); // 체크 상태에 따라 모달 이메일 목록 업데이트
    });
});

// 모달 관련 코드
var modal = document.getElementById("myModal");
var openModalBtn = document.getElementById("openModalBtn");
var closeBtn = document.getElementById("closeBtn");
let selectedEmails = [];  // 선택된 이메일을 저장할 배열

// 모달 열기
openModalBtn.onclick = function() {
    modal.style.display = "block";
}

// 체크박스를 클릭할 때 이메일을 모달에 추가하거나 제거
function addEmailToModal(event, checkbox) {
    const email = checkbox.getAttribute('data-email');
    
    if (checkbox.checked) {
        if (!selectedEmails.includes(email)) {
            selectedEmails.push(email);
        }
    } else {
        selectedEmails = selectedEmails.filter(e => e !== email);
    }
    
    updateEmailField();  // 이메일 목록을 모달에 업데이트
}

// 모달에 이메일 목록 업데이트
function updateEmailField() {
    const emailField = document.getElementById('email');
    emailField.value = selectedEmails.join(', ');  // 콤마로 구분된 이메일 목록 표시
}

cancelBtn.onclick = function() {
	document.getElementById('subject').value = null;
	document.getElementById('message').value =null;
    modal.style.display = "none";
}

// 모달 닫기
closeBtn.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// 이메일 전송 버튼 클릭
document.getElementById('submitBtn').addEventListener('click', function(event) {
    event.preventDefault();  // 폼 제출 방지

    const email = document.getElementById('email').value;
    const subject = document.getElementById('subject').value;
    const message = document.getElementById('message').value;
    
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
        if (res) {
            alert('보내기 성공!');
            modal.style.display = "none";
        }
    }).catch(function (err) {
        alert('실패!');
    });
});

let isAllSelected = false;  // 전체 선택 여부를 추적하는 변수

// 전체선택/전체해제 기능
function toggleSelectAll() {
    const checkboxes = document.querySelectorAll('input[name="check2"]');
    isAllSelected = !isAllSelected;  // 전체 선택 상태를 반전
    checkboxes.forEach(checkbox => {
        checkbox.checked = isAllSelected;
    });
}

// 체크박스 클릭 시 호출되는 함수
function handleCheckboxClick(event, checkbox) {
    event.stopPropagation();  // 클릭 이벤트의 전파를 방지
	
}

// 추가 버튼 클릭 시 입력창을 보여주는 함수
function openAddCategoryModal() {
    document.getElementById('addCategoryModal').style.display = 'block';
}

// 추가된 카테고리를 리스트에 추가하는 함수
function addCategory() {
    const newCategory = document.getElementById('newCategory').value.trim();
    if (newCategory) {
        // 새로운 카테고리를 테이블에 추가하는 로직 (여기서는 예시로 콘솔 출력)
		$.ajax({
		        type: 'POST',
		        url: '/api/admin/addCategory',
		        data: { category: newCategory }
		    }).then(function (res) {
		        if (res) {
		            alert('추가완료!');
					location.reload();
		        }
		    }).catch(function (err) {
		        alert('실패!');
		    });
        document.querySelector('tbody').appendChild(newRow);
        closeAddCategoryModal();
    } else {
        alert('카테고리를 입력하세요.');
    }
}

// 모달을 닫는 함수
function closeAddCategoryModal() {
    document.getElementById('addCategoryModal').style.display = 'none';
    document.getElementById('newCategory').value = '';  // 입력창 초기화
}

// 선택된 항목 삭제 함수
function deleteSelectedCategories() {
	const checkboxes = document.querySelectorAll('input[name="check2"]:checked');
    // 체크된 항목이 없을 경우 경고
    if (checkboxes.length === 0) {
        alert('선택된 카테고리가 없습니다.');
        return;  // 체크된 항목이 없으면 함수 종료
    }
    // 삭제 확인 경고창
    let confirmation = confirm("이 항목을 정말로 삭제하시겠습니까?");
    
    if (confirmation) {
        // 체크된 항목들이 있을 경우 삭제 요청
		
		const selectedCategories = Array.from(checkboxes).map(checkbox => checkbox.dataset.category);
		
		console.log(selectedCategories);
		
        $.ajax({
            type: 'POST',
            url: '/api/admin/deleteCategory',
			contentType: 'application/json',  // 서버에 JSON 형식으로 데이터 전송
            data: JSON.stringify({ selectedCategories: selectedCategories }),  // selectedCategories 키로 카테고리 배열을 보내기
        }).then(function (res) {
            if (res) {
                alert('삭제완료!');
                location.reload();  // 삭제 후 페이지 새로고침
            }
        }).catch(function (err) {
            alert('삭제 실패!');
        });
    }
}



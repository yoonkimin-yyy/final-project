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

// 상세 정보를 토글하는 함수
   function toggleDetails(userId) {
       var detailRow = document.getElementById('details-' + userId);

       // 다른 신청자 상세 정보가 보이면 숨김 처리
       var allDetails = document.querySelectorAll('.detail-info');
       allDetails.forEach(function(row) {
           if (row !== detailRow) {
               row.style.display = 'none';
           }
       });

       // 선택된 신청자의 상세 정보를 토글
       if (detailRow.style.display === 'none' || detailRow.style.display === '') {
           detailRow.style.display = 'block';
       } else {
           detailRow.style.display = 'none';
       }
   }

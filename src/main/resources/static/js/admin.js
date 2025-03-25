// 샘플 문의 데이터
const inquiries = [
    {
        id: 1,
        title: "로그인이 안되는 문제",
        name: "김철수",
        date: "2024-03-21",
        status: "answered"
    },
    {
        id: 2,
        title: "결제 오류 관련 문의",
        name: "이영희",
        date: "2024-03-20",
        status: "pending"
    },
    {
        id: 3,
        title: "회원가입 인증 메일",
        name: "박지성",
        date: "2024-03-19",
        status: "answered"
    }
];

// 문의 리스트 렌더링 함수
function renderInquiries(items) {
    const listContainer = document.getElementById('inquiryList');
    listContainer.innerHTML = items.map(item => `
        <div class="inquiry-item">
            <div class="inquiry-title">${item.title}</div>
            <div class="inquiry-meta">
                <span>${item.name} | ${item.date}</span>
                <span class="inquiry-status status-${item.status}">
                    ${item.status === 'answered' ? '답변완료' : '답변대기'}
                </span>
            </div>
        </div>
    `).join('');
}

// 검색 기능        // 검색 기능
function handleSearch() {
    const searchType = document.getElementById('searchType').value;
    const searchTerm = document.getElementById('searchInput').value.toLowerCase();

    const filteredInquiries = inquiries.filter(inquiry => {
        if (searchType === 'title') {
            return inquiry.title.toLowerCase().includes(searchTerm);
        } else if (searchType === 'name') {
            return inquiry.name.toLowerCase().includes(searchTerm);
        } else {
            // 전체 검색
            return inquiry.title.toLowerCase().includes(searchTerm) ||
                   inquiry.name.toLowerCase().includes(searchTerm);
        }
    });
    
    renderInquiries(filteredInquiries);
}

// 검색어 입력 이벤트
document.getElementById('searchInput').addEventListener('input', handleSearch);

// 검색 타입 변경 이벤트
document.getElementById('searchType').addEventListener('change', handleSearch);

// 폼 제출 처리
document.getElementById('contactForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    // 새로운 문의 추가
    const newInquiry = {
        id: inquiries.length + 1,
        title: document.getElementById('subject').value,
        name: document.getElementById('name').value,
        date: new Date().toISOString().split('T')[0],
        status: 'pending'
    };
    
    inquiries.unshift(newInquiry);
    renderInquiries(inquiries);
    
    alert('문의가 성공적으로 등록되었습니다.');
    this.reset();
});

// 초기 문의 리스트 렌더링
renderInquiries(inquiries);
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


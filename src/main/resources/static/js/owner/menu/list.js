function toggleDetail(clickedRow) {
    const nextRow = clickedRow.nextElementSibling;
	// 특정 class를 포함하면 함수가 실행되지 않음
	if (event.target.classList.contains('no-toggle')) {
	       return;
	   }
    // 상세 정보가 표시되는 <tr>인지 확인
    if (nextRow && nextRow.classList.contains('detail-row')) {
        const detailDiv = nextRow.querySelector('.detail-info');

        // 현재 상세 내용이 보이면 숨김
        if (nextRow.style.display === 'table-row') {
            nextRow.style.display = 'none';
        } else {
            // 다른 상세 내용은 닫지 않고, 현재 선택된 것만 토글
            nextRow.style.display = 'table-row';
        }
    }
}
function openInsertMenu(bakeryNo) {
	const url = `/bakery/menu/insert/form?bakeryNo=${bakeryNo}`;
	    window.open(url, "_blank", "width=600, height=400, top=100, left=100");
}
function openUpdateMenu(menuNo,bakeryNo) {
	const url = `/bakery/menu/update/form?menuNo=${menuNo}&bakeryNo=${bakeryNo}`;
	    window.open(url, "_blank", "width=600, height=400, top=100, left=100");
}
function confirmDelete(button) {
    let form = button.closest('form'); // 현재 클릭된 버튼의 부모 폼 찾기
    let menuNo = form.querySelector('input[name="menuNo"]').value; // 폼 내 menuNo 가져오기

    if (confirm(`정말 삭제하시겠습니까?`)) {
        form.submit();
    }
}
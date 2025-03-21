function toggleDetail(clickedRow) {
	// 특정 class를 포함하면 함수가 실행되지 않음
	if (event.target.classList.contains('no-toggle')) {
	       return;
	   }
	   
    const nextRow = clickedRow.nextElementSibling;
    // 상세 정보가 표시되는 <tr>인지 확인
    if (nextRow && nextRow.classList.contains('detail-row')) {
        const detailDiv = nextRow.querySelector(`.detail-info`);

        // 현재 상세 내용이 보이면 숨김
        if (nextRow.style.display === 'table-row') {
            nextRow.style.display = 'none';
        } else {
            // 다른 상세 내용은 닫지 않고, 현재 선택된 것만 토글
            nextRow.style.display = 'table-row';
        }
    }
}
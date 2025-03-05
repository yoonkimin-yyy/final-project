// 스크립트를 DOMContentLoaded 이벤트 내에서 실행
document.addEventListener('DOMContentLoaded', () => {
    // "전체 동의" 체크박스
    const checkAll = document.querySelector('.check-all-div input[type="checkbox"]');
    // 모든 체크박스 (필수 + 선택)
    const allCheckboxes = document.querySelectorAll('.list-ul input[type="checkbox"]');
    // 필수 체크박스 (필수 항목에만 해당)
    const requiredCheckboxes = document.querySelectorAll('.essential-div input[type="checkbox"]');
    // "다음" 버튼
    const nextButton = document.querySelector('.check-btn');

    // 전체 동의 클릭 시 모든 체크박스 선택/해제
    if (checkAll) {
        checkAll.addEventListener('change', () => {
            allCheckboxes.forEach((checkbox) => {
                checkbox.checked = checkAll.checked;
            });
        });
    }

    // 개별 체크박스 클릭 시 전체 동의 상태 업데이트
    allCheckboxes.forEach((checkbox) => {
        checkbox.addEventListener('change', () => {
            const allChecked = Array.from(allCheckboxes).every(cb => cb.checked);
            if (checkAll) {
                checkAll.checked = allChecked;
            }
        });
    });

    // "다음" 버튼 클릭 시 필수 항목 확인 및 페이지 이동
    if (nextButton) {
        nextButton.addEventListener('click', () => {
            // 필수 항목이 모두 선택되었는지 확인
            const allRequiredChecked = Array.from(requiredCheckboxes).every(cb => cb.checked);
            if (!allRequiredChecked) {
                alert('필수 항목에 모두 동의해 주세요.');
            } else {
                // 모든 필수 항목이 체크되었을 때 처리할 동작
                alert('다음 단계로 이동합니다.');
                window.location.href = './loginup.html'; // 이동할 회원가입 페이지 경로
            }
        });
    }
});

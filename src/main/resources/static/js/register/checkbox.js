document.addEventListener('DOMContentLoaded', () => {
    // 전체 동의
    const checkAll = document.querySelector('.check-all-div input[type="checkbox"]');
    // 모든 체크박스 (필수 + 선택)
    const allCheckboxes = document.querySelectorAll('.list-ul input[type="checkbox"]');
    // 필수 체크박스
    const requiredCheckboxes = document.querySelectorAll('.essential-div input[type="checkbox"]');
    // 다음 버튼
    const nextButton = document.querySelector('.check-btn');

    // 전체 동의 클릭 시 모든 체크박스 선택/해제
    if (checkAll) {
        checkAll.addEventListener('change', () => {
            allCheckboxes.forEach((checkbox) => {
                checkbox.checked = checkAll.checked;
            });
        });
    }

		    if (nextButton) {
	        nextButton.addEventListener('click', function () {
	            // 모든 필수 항목이 체크되었는지 확인
	            const allRequiredChecked = Array.from(requiredCheckboxes).every(cb => cb.checked);

	            if (!allRequiredChecked) {
	                alert('필수 항목에 모두 동의해 주세요.');
	                return; 
	            }

	            // user 아님 business로 가져와서 페이지 이동
	            const urlParams = new URLSearchParams(window.location.search);
	            const userType = urlParams.get('type') || 'user';
				
				if(userType === 'user') {
					location.href = '/register/loginup'
					alert('다음 단계로 이동합니다.');
				}else {
					location.href = '/register/businessloginup'
					alert('다음 단계로 이동합니다.');
				}

	        });
	    }

});
document.addEventListener('DOMContentLoaded', () => {
    // 전체 동의 체크박스
    const checkAll = document.querySelector('.check-all-div input[type="checkbox"]');
    // 모든 체크박스 (필수 + 선택)
    const allCheckBoxes = document.querySelectorAll('.list-ul input[type="checkbox"]');
    // 필수 체크박스
    const requiredCheckboxes = document.querySelectorAll('.essential-div input[type="checkbox"]');
	// 위치 체크박스 (선택)
	const locationBox = document.getElementById('location');
    // 마케팅 체크박스 (단일 요소)
    const marketingBox = document.getElementById('marketing');
    // 이메일 체크박스
	const emailCheckBox = document.getElementById('email');
	// SMS 체크박스
	const smsCheckBox = document.getElementById('sms');
    // 다음 버튼
    const nextButton = document.getElementById("check-btn");


	// 체크박스 값 변경 함수 (Y/N 설정)
	function updateCheckboxValue(checkbox) {
	    checkbox.value = checkbox.checked ? "Y" : "N";
	}
	   
	// 전체 동의 클릭 시 모든 체크박스 선택/해제 + 값 변경
	if (checkAll) {
	    checkAll.addEventListener('change', () => {
	        allCheckBoxes.forEach((checkbox) => {
	            checkbox.checked = checkAll.checked;
	            updateCheckboxValue(checkbox); // Y/N 값도 함께 변경
	        });
	    });
	}

	// 이메일,SMS 체크박스를 배열로 선언
	   const snsCheckBoxes = [emailCheckBox, smsCheckBox];

	   // 마케팅 체크박스가 변경될 때 이메일 & SMS 체크/해제 + 값 변경
	   if (marketingBox) {
	       marketingBox.addEventListener('change', () => {
	           snsCheckBoxes.forEach((checkbox) => {
	               checkbox.checked = marketingBox.checked;
	               updateCheckboxValue(checkbox); 
	           });
	           updateCheckboxValue(marketingBox); 
	       });
	   }

	   // 이메일 또는 SMS 체크 시 마케팅 체크박스 자동 체크 + 값 변경
	   snsCheckBoxes.forEach((checkbox) => {
	       checkbox.addEventListener('change', () => {
	           // 하나라도 체크되어 있으면 마케팅 체크박스 체크
	           marketingBox.checked = snsCheckBoxes.some(box => box.checked);
	           updateCheckboxValue(marketingBox); 
	       });
	   });

	   // 모든 체크박스에 Y/N 값 설정 이벤트 추가 (위치, 마케팅, 이메일, SMS)
	   const checkBoxes = [locationBox, marketingBox, emailCheckBox, smsCheckBox];

	   checkBoxes.forEach((checkbox) => {
	       checkbox.addEventListener("change", function () {
	           updateCheckboxValue(this);
	       });

	       // 초기값 N
	       checkbox.value = "N";
	   });
    // 다음 버튼 클릭 이벤트 추가
    if (nextButton) {
        nextButton.addEventListener("click", function () {
            // 모든 필수 항목이 체크되었는지 확인
            const allRequiredChecked = Array.from(requiredCheckboxes).every(cb => cb.checked);

            if (!allRequiredChecked) {
                alert('필수 항목에 모두 동의해 주세요.');
                return; // 필수 체크 안 하면 여기서 중단됨
            }

            // 모든 체크박스가 체크되었으면 폼 제출 실행
            document.getElementById("check-form").submit();

            alert('다음 단계로 이동합니다.');

        });
    }
});

document.addEventListener('DOMContentLoaded', () => {
    const infoForm = document.querySelector('.info-form');
    const nameInput = document.getElementById('info-name');
    const emailInput = document.getElementById('info-email');
    const phoneInput = document.getElementById('info-phone');
    const passwordNow = document.getElementById('password-now');
    const passwordChange = document.getElementById('password-new');
    const passwordConfirm = document.getElementById('password-new-confirm');

    // 비밀번호 변경 
    const passDiv = document.getElementById('pass-div');
    const passBtn = document.getElementById('pass-btn');

    // 주소 변경
    const postcoderDiv = document.getElementById('postcode-div');
    const addrDiv = document.getElementById('addr-div');
    const addrBtn = document.getElementById('addr-btn');

    // 버튼
    const changePwBtn = document.querySelector('.change-pw-btn');
    const changeArBtn = document.querySelector('.change-ar-btn');
    const editBtn = document.querySelector('.edit-btn');

    // 메세지
    const nameMsg = document.getElementById('name-msg');
    const emailMsg = document.getElementById('email-msg');
    const phoneMsg = document.getElementById('phone-msg');
    const nowMsg = document.getElementById('passwordNow-msg');
    const newMsg = document.getElementById('passwordNew-msg');
    const confirmMsg = document.getElementById('password-confirm-msg');

    // 수정 버튼 
    editBtn.addEventListener('click', () => {
        if (updateForm()) {
            alert('수정되었습니다.');
            infoForm.submit();
        } else {
            alert('수정하려는 내용을 입력해주세요.');
        }
    });


    // 유효성 검사
    function updateForm() {
        let isUpdate = true;

        nameMsg.textContent = '';
        emailMsg.textContent = '';
        phoneMsg.textContent = '';

        if (nameInput.value.trim() === '') {
            nameMsg.textContent = '이름을 입력해 주세요';
            isUpdate = false;
        }else if (!/^[가-힣]{2,10}$/.test(nameInput.value)) {
            nameMsg.textContent = '이름은 한글만 가능합니다.';
            isUpdate = false;
        } else {
            nameMsg.textContent = '';
        }

        if (emailInput.value.trim() === '') {
            emailMsg.textContent = '이메일을 입력해 주세요';
            isUpdate = false;
        }else if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(emailInput.value)) {
            emailMsg.textContent = '올바른 이메일 형식을 입력해주세요.';
            isUpdate = false;
        }

        if (phoneInput.value.trim() === '') {
            phoneMsg.textContent = '번호를 -없이 입력해 주세요';
            isUpdate = false;
        }else if (!/^010\d{4}\d{4}$/.test(phoneInput.value)) {
            phoneMsg.textContent = '휴대폰 번호는 숫자만 입력 가능합니다.';
            isUpdate = false;
        } else {
            phoneMsg.textContent = '';
        }

        return isUpdate;
    }

    // 비밀번호 버튼
    
    passDiv.style.display = 'flex';
    passDiv.style.display = 'none';

    changePwBtn.addEventListener('click', () => {

        if (passDiv.style.display === 'flex') {
            passDiv.style.display = 'none'; // 숨기게
        } else {
            passDiv.style.display = 'flex'; // 보이게
        }
    });

    passBtn.addEventListener('click', () => {
        if(updatepassForm()) {
            alert("변경되었습니다!");
            infoForm.submit();
        }else {
            alert("변경 사항을 입력해 주세요.")
        }
    });

    // 주소 버튼

    postcoderDiv.style.display = 'flex';
    postcoderDiv.style.display = 'none';
    addrDiv.style.display = 'flex';
    addrDiv.style.display = 'none';

    changeArBtn.addEventListener('click', () => {

        if(postcoderDiv.style.display === 'flex' && addrDiv.style.display === 'flex') {
            postcoderDiv.style.display = 'none';
            addrDiv.style.display = 'none';
        }else {
            postcoderDiv.style.display = 'flex';
            addrDiv.style.display = 'flex';
        }
    });

    addrBtn.addEventListener('click', () =>{
        alert('변경되었습니다.')
    });



    function updatepassForm() {
        let isPass = true;

        nowMsg.textContent = '';
        newMsg.textContent = '';
        passwordConfirm.textContent = '';

    // 현재 비밀번호
    if (passwordNow.value.trim() === '') {
        nowMsg.textContent = '비밀번호를 입력해 주세요';
        isPass = false;
    }else if (!/^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/.test(passwordNow.value)) {
        nowMsg.textContent = '비밀번호는 영문자, 숫자, \n 특수문자를 포함해 8~20자여야 합니다.';
        isPass = false;
    } else {
        nowMsg.textContent = '';
    }

    // 새 비밀번호
    if (passwordChange.value.trim() === '') {
        newMsg.textContent = '사용하실 비밀번호를 입력해 주세요';
        isPass = false;
    }else if (!/^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/.test(passwordChange.value)) {
        newMsg.textContent = '비밀번호는 영문자, 숫자, \n 특수문자를 포함해 8~20자여야 합니다.';
        isPass = false;
    } else {
        newMsg.textContent = '';
    }
    
    // 새 비밀번호 확인
    if (passwordConfirm.value !== passwordConfirm.value) {
        confirmMsg.textContent = '비밀번호가 일치하지 않습니다.';
        isPass = false;
    } else {
        confirmMsg.textContent = '';
    }

    return isPass;
    };
});

const addressInput = document.getElementById('sample6_address');
const postcodeInput = document.getElementById('sample6_postcode');

window.sample6_execDaumPostcode = function () {
    const width = 500;  // 팝업창 너비
    const height = 600; // 팝업창 높이
    const left = (window.screen.width / -3) - (width / 3); // 중앙 정렬 (가로)
    const top = (window.screen.height / 2) - (height / 2); // 중앙 정렬 (세로)

    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("sample6_detailAddress").value = extraAddr;

            } else {
                document.getElementById("sample6_detailAddress").value = '';
            }
			

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById('sample6_address').value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById('sample6_detailAddress').focus();
			
			const parts = document.getElementById('sample6_address').value.split(" ");
			    const city = parts[0];  // 서울
			    const district = parts[1]; // 강남구
			    const streetAndNumber = parts.slice(2).join(" "); // 논현로123길 4-1

			    // street과 number 분리
			    const match = streetAndNumber.match(/(.+?)(\d+-\d+|\d+)$/);
			    const street = match ? match[1].trim() : "";
			    const number = match ? match[2] : "";

			    // input 요소에 값 설정
			    document.getElementById("city").value = city;
			    document.getElementById("district").value = district;
			    document.getElementById("street").value = street;
			    document.getElementById("number").value = number;
        }
    }).open({
        left: left,
        top: top
    });
}

const parts = document.getElementById('sample6_address').value.split(" ");
    const city = parts[0];  // 서울
    const district = parts[1]; // 강남구
    const streetAndNumber = parts.slice(2).join(" "); // 논현로123길 4-1

    // street과 number 분리
    const match = streetAndNumber.match(/(.+?)(\d+-\d+|\d+)$/);
    const street = match ? match[1].trim() : "";
    const number = match ? match[2] : "";

    // input 요소에 값 설정
    document.getElementById("city").value = city;
    document.getElementById("district").value = district;
    document.getElementById("street").value = street;
    document.getElementById("number").value = number;
	
function checkValue(){
	const postCode = document.getElementById('sample6_postcode').value
	const address = document.getElementById('sample6_address').value
	const city = document.getElementById('city').value
	console.log(postCode)
	console.log(address)
	console.log(city)
}

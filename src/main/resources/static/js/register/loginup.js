
    const form = document.querySelector('.loginup-form');
    const idInput = document.getElementById('userid');
    const passwordInput = document.getElementById('password');
    const passwordConfirmInput = document.getElementById('password-confirm');
    const emailInput = document.getElementById('email');
    const phoneInput = document.getElementById('phone');
    const addressInput = document.getElementById('sample6_address');
    const postcodeInput = document.getElementById('sample6_postcode');
    const birthdateInput = document.getElementById('birthdate');
    const nameInput = document.getElementById('username');
    const authInput = document.getElementById('phone-auth');
    const authButton = document.getElementById('phone-btn');
    const submitButton = document.querySelector('.loginup-btn');
    const checkButton = document.getElementById('check-btn');
	const postcodeBtn = document.getElementById('postcode-btn');

    const idMsg = document.getElementById('id-msg');
    const passwordMsg = document.getElementById('password-msg');
    const passwordConfirmMsg = document.getElementById('password-confirm-msg');
    const emailMsg = document.getElementById('email-msg');
    const phoneMsg = document.getElementById('phone-msg');
    const addressMsg = document.getElementById('address-msg');
    const postcodeMsg = document.getElementById('postcode-msg');
    const birthdayMsg = document.getElementById('birthday-msg');
    const nameMsg = document.getElementById('name-msg');
    const authMsg = document.getElementById('phone-auth-msg');

    authInput.style.display = 'none';
	checkButton.style.display = 'none';
	submitButton.style.display = 'none';

    // 아이디 중복 체크 
    let isIdChecked = false;
	
	// 이메일 중복 체크 
	let isEmailCheckd = false;


    // 아이디 유효성 검사 함수
    function validateId() {
        const getId = $("#userid").val().trim(); // 공백 제거

        // 아이디 유효성 검사 (5~20자 & 영문 소문자)
        if (getId === "") {
            $("#id-msg").css("color", "red").text("* 아이디는 필수 입력 항목입니다.");
            isIdValid = false;
            return false;
        }
		if (!/^(?=.*[a-z])[a-z0-9]{5,20}$/.test(getId)) {
		    $("#id-msg").css("color", "red").text("아이디는 영문 소문자 5~20자로 입력해야 합니다.");
		    isIdValid = false;
		    return false;
		}
		
        // 유효성 검사 통과
        $("#id-msg").text("");
        isIdValid = true;
        return true;
    }

    // 아이디 중복 체크 함수 (유효성 검사 후 실행)
    function checkId() {
        const getId = $("#userid").val().trim(); // 공백 제거

        // 먼저 아이디 유효성 검사를 통과했는지 확인
        if (!validateId()) {
            $(".phone-btn").attr("disabled", true); // 인증번호 받기 버튼 비활성화
            return;
        }

        // 유효성 검사 통과 후 AJAX 실행
        $.ajax({
            type: "GET",
            url: "/member/checkId/" + getId,
            success: function (res) {
                console.log("AJAX 응답 결과:", res);
                if (res) {
                    $("#id-msg").css("color", "red").text("사용 불가능한 아이디입니다.");
                    isIdChecked = true;
                    $(".phone-btn").attr("disabled", true);
                } else {
                    $("#id-msg").css("color", "green").text("사용 가능한 아이디입니다.");
                    isIdChecked = true;
                    $(".phone-btn").removeAttr("disabled");
                }
            },
            error: function (err) {
                console.error("서버 오류:", err);
                $("#id-msg").css("color", "red").text("서버 오류가 발생했습니다.");
                isIdChecked = false;
                $(".phone-btn").attr("disabled", true);
            }
        });
    }
	
	// 비밀번호 유효성검사, 비밀번호 확인
	function validatePassword() {
	    const password = passwordInput.value;
	    const passwordConfirm = passwordConfirmInput.value;
	    const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/;

	    // 비밀번호 유효성 검사
	    if (password.trim() === '') {
	        passwordMsg.textContent = '* 비밀번호는 필수 입력 항목입니다.';
	        passwordMsg.style.color = 'red';
	    } else if (!passwordRegex.test(password)) {
	        passwordMsg.textContent = '비밀번호는 영문자, 숫자, 특수문자를 포함해 8~20자여야 합니다.';
	        passwordMsg.style.color = 'red';
	    } else {
	        passwordMsg.textContent = '사용 가능한 비밀번호입니다.';
	        passwordMsg.style.color = 'green';
	    }

	    // 비밀번호 확인 검사
	    if (passwordConfirm.trim() !== '' && password !== passwordConfirm) {
	        passwordConfirmMsg.textContent = '비밀번호가 일치하지 않습니다.';
	        passwordConfirmMsg.style.color = 'red';
	    } else if (passwordConfirm.trim() !== '') {
	        passwordConfirmMsg.textContent = '비밀번호가 일치합니다.';
	        passwordConfirmMsg.style.color = 'green';
	    } else {
	        passwordConfirmMsg.textContent = '';
	    }
	}

	// 비밀번호 입력할 때마다 검사 실행 
	passwordInput.addEventListener('input', validatePassword);
	passwordConfirmInput.addEventListener('input', validatePassword);
	
	// 이름 실시간 유효성 검사
	function validateName() {
	    const name = nameInput.value.trim();
	    const nameRegex = /^[가-힣]{2,10}$/; // 한글 2~10자

	    if (name === '') {
	        nameMsg.textContent = '* 이름은 필수 입력 항목입니다.';
	        nameMsg.style.color = 'red';
	    } else if (!nameRegex.test(name)) {
	        nameMsg.textContent = '이름은 한글만 가능하며 2자 이상, 10자 이하여야 합니다.';
	        nameMsg.style.color = 'red';
	    } else {
	        nameMsg.textContent = '올바른 이름 형식입니다.';
	        nameMsg.style.color = 'green';
	    }
	}

	// 입력할 때마다 검사 실행
	nameInput.addEventListener('input', validateName);
	
	// 이메일 유효성 검사 및 중복 체크 함수 
	async function validateAndCheckEmail() {
	    const userEmail = document.getElementById("email").value.trim();
	    const emailMsg = document.getElementById("email-msg");
	    const authButton = document.getElementById("phone-btn"); 

	    // 이메일 실시간 유효성 검사 
	    if (userEmail === "") {
	        emailMsg.style.color = "red";
	        emailMsg.textContent = "* 이메일은 필수 입력 항목입니다.";
	        isEmailChecked = false;
	        authButton.disabled = true; // 버튼 비활성화
	        return;
	    }
	    if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(userEmail)) {
	        emailMsg.style.color = "red";
	        emailMsg.textContent = "유효한 이메일 형식이 아닙니다.";
	        isEmailChecked = false;
	        authButton.disabled = true; // 버튼 비활성화
	        return;
	    }

	    try {
	        // 유효한 이메일이면 AJAX로 중복 체크 실행
	        const response = await fetch(`/member/checkEmail/${userEmail}`);
	        const isDuplicate = await response.json(); // JSON 응답 처리

	        if (isDuplicate) {
	            emailMsg.style.color = "red";
	            emailMsg.textContent = "이미 사용 중인 이메일입니다.";
	            isEmailChecked = false;
	            authButton.disabled = true; // 버튼 비활성화
	        } else {
	            emailMsg.style.color = "green";
	            emailMsg.textContent = "사용 가능한 이메일입니다.";
	            isEmailChecked = true;
	            authButton.disabled = false; // 버튼 활성화
	        }
	    } catch (error) {
	        console.error("서버 오류:", error);
	        emailMsg.style.color = "red";
	        emailMsg.textContent = "서버 오류가 발생했습니다.";
	        isEmailChecked = false;
	        authButton.disabled = true; // 버튼 비활성화
	    }
	}

	// 이메일 입력 필드에서 자동 실행되도록 이벤트 추가
	document.getElementById("email").addEventListener("input", validateAndCheckEmail);


    // 문서가 로딩된 후 실행
    $(document).ready(function () {

        $("#id-check-btn").click(checkId);  // 중복 체크 버튼 클릭 시 실행
        $("#userid").on("input", validateId); //  아이디 입력 시 유효성 검사 실행
    });
	
	// 핸드폰 번호 실시간 유효성 검사
	function validatePhone() {
	    const phoneNumber = phoneInput.value.trim();
	    const phoneRegex = /^010\d{4}\d{4}$/;

	    if (phoneNumber === '') {
	        phoneMsg.textContent = '* 휴대폰 번호는 필수 입력 항목입니다.';
	        phoneMsg.style.color = 'red';
	    } else if (!phoneRegex.test(phoneNumber)) {
	        phoneMsg.textContent = '휴대폰 번호는 010을 포함한 숫자 11자리로 입력해주세요.';
	        phoneMsg.style.color = 'red';
	    } else {
	        phoneMsg.textContent = '번호 인증을 진행해주세요';
	        phoneMsg.style.color = 'green';
	    }
	}

	// 입력할 때마다 검사 실행
	phoneInput.addEventListener('input', validatePhone);

	// 인증번호 받기 버튼 눌렀을때 유효성 검사
    authButton.addEventListener('click', () => {
        // 유효성 검사 실행
        if (!validateForm()) {
            return;
        }
        if (!isIdChecked) {
            return false;
        }


        // 인증번호 로직 실행
        if (authButton.textContent === '인증번호받기') {
            idInput.setAttribute('readonly', true);
            passwordInput.setAttribute('readonly', true);
            passwordConfirmInput.setAttribute('readonly', true);
            emailInput.setAttribute('readonly', true);
            addressInput.setAttribute('readonly', true);
            postcodeInput.setAttribute('readonly', true);
            birthdateInput.setAttribute('readonly', true);
            nameInput.setAttribute('readonly', true);
            phoneInput.setAttribute('readonly', true);
			postcodeBtn.disabled = true;
            authInput.style.display = 'block';
            authButton.textContent = '인증번호 재요청';
            submitButton.style.display = 'none';
            checkButton.style.display = 'block';
        }
    });

    // 유효성 검사
    function validateForm() {
        let isValid = true;

        if (idInput.value.trim() === '') {
            idMsg.textContent = '* 아이디는 필수 입력 항목입니다.';
            isValid = false;
        } else if (!/^(?=.*[a-z])[a-z0-9]{5,20}$/.test(idInput.value)) {
            idMsg.textContent = '아이디는 영문 소문자 5~20자여야 합니다.';
            isValid = false;
        } else {
            idMsg.textContent = '';
        }

        if (passwordInput.value.trim() === '') {
            passwordMsg.textContent = '* 비밀번호는 필수 입력 항목입니다.';
            isValid = false;
        } else if (!/^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/.test(passwordInput.value)) {
            passwordMsg.textContent = '비밀번호는 영문자, 숫자, 특수문자를 포함해 8~20자여야 합니다.';
            isValid = false;
        } else {
            passwordMsg.textContent = '';
        }

        if (passwordInput.value !== passwordConfirmInput.value) {
            passwordConfirmMsg.textContent = '비밀번호가 일치하지 않습니다.';
            isValid = false;
        } else {
            passwordConfirmMsg.textContent = '';
        }

        if (emailInput.value.trim() === '') {
            emailMsg.textContent = '* 이메일은 필수 입력 항목입니다.';
            isValid = false;
        } else if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(emailInput.value)) {
            emailMsg.textContent = '올바른 이메일 형식을 입력해주세요.';
            isValid = false;
        } else {
            emailMsg.textContent = '';
        }

        if (phoneInput.value.trim() === '') {
            phoneMsg.textContent = '* 휴대폰 번호는 필수 입력 항목입니다.';
            isValid = false;
        } else if (!/^010\d{4}\d{4}$/.test(phoneInput.value)) {
            phoneMsg.textContent = '휴대폰 번호는 숫자만 입력 가능합니다.';
            isValid = false;
        } else {
            phoneMsg.textContent = '';
        }

        if (postcodeInput.value.trim() === '') {
            postcodeMsg.textContent = '* 우편번호는 필수 입력 항목입니다.';
            isValid = false;
            console.log(postcodeInput);
        } else {
            postcodeMsg.textContent = '';
        }

        if (addressInput.value.trim() === '') {
            addressMsg.textContent = '* 주소는 필수 입력 항목입니다.';
            isValid = false;
            console.log(addressInput);
        } else {
            addressMsg.textContent = '';
        }

        if (birthdateInput.value.trim() === '') {
            birthdayMsg.textContent = '* 생년월일은 필수 입력 항목입니다.';
            isValid = false;
        } else if (!birthdateInput.value) {
            birthdayMsg.textContent = '생년월일을 입력해주세요.';
            isValid = false;
        } else {
            birthdayMsg.textContent = '';
        }

        if (nameInput.value.trim() === '') {
            nameMsg.textContent = '* 이름은 필수 입력 항목입니다.';
            isValid = false;
        } else if (!/^[가-힣]{2,10}$/.test(nameInput.value)) {
            nameMsg.textContent = '이름은 한글만 가능하며 2자 이상, 10자 이하여야 합니다.';
            isValid = false;
        } else {
            nameMsg.textContent = '';
        }

        return isValid;
    }

    // 가입하기 버튼
    submitButton.addEventListener('click', (event) => {
        const inputText = document.getElementById('sample6_address');
        const [userCity, userDistrict, userStreet, userNumber] = inputText.value.split(' ');

        const city = userCity.trim();
        const district = userDistrict.trim();
        const street = userStreet.trim();
        const number = userNumber.trim();

        const data = {
            city: city,
            district: district,
            street: street,
            number: number
        }

        document.getElementById('city').value = city;
        document.getElementById('district').value = district;
        document.getElementById('street').value = street;
        document.getElementById('number').value = number;
    });

// 주소 api
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
        }
    }).open({
        left: left,
        top: top
    });
}


// 인증번호 요청 (SMS 전송)
function sendSMS() {
    let phoneNumber = $("#phone").val();
    let phoneMsg = $("#phone-msg");
    let authMsg = $("#phone-auth-msg");

    if (!phoneNumber) {
        phoneMsg.text("전화번호를 입력하세요.").css("color", "red");
        return;
	}else if(!validateForm()) {
		alert("입력하신 내용을 확인해 주세요.")
		return;
	}else if (!isIdChecked) {
		alert("아이디 중복 체크는 필수 입니다!")
	    return;
	}

    phoneMsg.text(""); // 오류 메시지 초기화

    $.ajax({
        url: "/sms/send",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({ phoneNumber: phoneNumber }),
        success: function (response) {
            phoneMsg.text("인증번호가 전송되었습니다.").css("color", "green");
            alert(response);

			checkButton.style.display = 'block';
        },
        error: function (xhr) {
            alert("SMS 전송 실패");
            phoneMsg.text("SMS 전송 실패").css("color", "red");
        }
    });
}

// 인증번호 검증 (입력한 인증번호 확인)
function checkSMS() {
    let phoneNumber = $("#phone").val();
    let code = $("#phone-auth").val();
    let authMsg = $("#phone-auth-msg");
    let phoneMsg = $("#phone-msg");


    if (!phoneNumber) {
        phoneMsg.text("전화번호를 입력하세요.").css("color", "red");
        return;
    }
    if (!code) {
        phoneMsg.text("인증번호를 입력하세요.").css("color", "red");
        return;
    }

    phoneMsg.text(""); // 오류 메시지 초기화

    $.ajax({
        url: "/sms/verify",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({ phoneNumber: phoneNumber, code: code }), // JSON 변환
        success: function (response) {
            alert(response);
            authMsg.text("인증 완료되었습니다.").css("color", "green");

            // "인증번호 확인" 버튼 숨기기
            $("#phone-btn").hide();
			checkButton.style.display = 'none';
			submitButton.style.display = 'block';
			authInput.style.display = 'none';
        },
        error: function (xhr) {
            alert("인증 실패");
            authMsg.text("인증 실패! 올바른 인증번호를 입력하세요.").css("color", "red");
        }

    });
}

    const form = document.querySelector('.loginup-form');
    const idInput = document.getElementById('userid');
    const passwordInput = document.getElementById('password');
    const passwordConfirmInput = document.getElementById('password-confirm');
    const emailInput = document.getElementById('email');
    const phoneInput = document.getElementById('phone');
    const birthdateInput = document.getElementById('birthdate');
    const nameInput = document.getElementById('username');
    const authInput = document.getElementById('phone-auth');
    const authButton = document.getElementById('phone-btn');
    const submitButton = document.querySelector('.loginup-btn');
    const checkButton = document.getElementById('check-btn');
	const businessNumber = document.getElementById('business-number');

    const idMsg = document.getElementById('id-msg');
    const passwordMsg = document.getElementById('password-msg');
    const passwordConfirmMsg = document.getElementById('password-confirm-msg');
    const emailMsg = document.getElementById('email-msg');
    const phoneMsg = document.getElementById('phone-msg');
    const birthdayMsg = document.getElementById('birthday-msg');
    const nameMsg = document.getElementById('name-msg');
    const authMsg = document.getElementById('phone-auth-msg');
	const businessMsg = document.getElementById('business-msg');

    authInput.style.display = 'none';
	checkButton.style.display = 'none';
	submitButton.style.display = 'none';
	

    // 아이디 중복 체크 상태 변수
    let isIdChecked = false;
	
	// 이메일 중복 체크 
		let isEmailCheckd = false;

    // 아이디 유효성 검사 함수
    function validateId() {
        const getId = $("#userid").val().trim(); // 공백 제거

        // 아이디 유효성 검사 (5~20자 & 영문자+숫자 조합)
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
        const getId = $("#userid").val().trim(); 

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

	// 핸드폰 번호 실시간 유효성 검사
		function validatePhone() {
		    const phoneNumber = phoneInput.value.trim();
		    const phoneRegex = /^010\d{4}\d{4}$/;

		    if (phoneNumber === '') {
		        phoneMsg.textContent = '* 휴대폰 번호는 필수 입력 항목입니다.';
		        phoneMsg.style.color = 'red';
		    } else if (!phoneRegex.test(phoneNumber)) {
		        phoneMsg.textContent = '휴대폰 번호는 숫자만 입력 가능합니다.';
		        phoneMsg.style.color = 'red';
		    } else {
		        phoneMsg.textContent = '번호 인증을 진행해주세요';
		        phoneMsg.style.color = 'green';
		    }
		}

		// 입력할 때마다 검사 실행 (실시간 반응)
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
				businessNumber.setAttribute('readonly', true);
	            emailInput.setAttribute('readonly', true);
	            birthdateInput.setAttribute('readonly', true);
	            nameInput.setAttribute('readonly', true);
	            phoneInput.setAttribute('readonly', true);
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
            phoneMsg.textContent = '휴대폰 번호는 010을 포함한 11자리 숫자만 입력 가능합니다.';
            isValid = false;
        } else {
            phoneMsg.textContent = '';
        }

        if (businessNumber.value.trim() === '') {
            businessMsg.textContent = '* 사업자번호는 필수 입력 항목입니다.';
            isValid = false;
        } else {
            businessMsg.textContent = '';
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

// 사업자 번호 api
function corp_chk() {
	let businessMsg = $("#business-msg");
	   let authButton = $("#phone-btn");
	   let businessNumberInput = $("#business-number");

	   // 숫자만 입력 가능하도록 처리
	   businessNumberInput.val(businessNumberInput.val().replace(/[^0-9]/g, ""));
	   let reg_num = businessNumberInput.val();

    if(!reg_num) {
        businessMsg.css("color", "red").text("사업자번호를 입력해주세요.");
		authButton.prop("disabled", true); // 인증번호받기 버튼 비활성화
        return false;
    }

    var data = {
        "b_no": [reg_num]
    };
    
    $.ajax({
        url: "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=%2F4gTZ0eaaAdo1FrLXV7K7ljwbH9m2FV6UUYzKswmYeeJwpjuHP9eAVuKks2c8Cn8zr7II6pg0hZAsuzpVP79fw%3D%3D",  // serviceKey 값을 xxxxxx에 입력
        type: "POST",
        data: JSON.stringify(data), // json 을 string으로 변환하여 전송
        dataType: "JSON",
        traditional: true,
        contentType: "application/json; charset:UTF-8",
        accept: "application/json",
        success: function(result) {
            console.log(result);
			 if (result.match_cnt == "1") {
			                // 유효한 사업자번호면 중복 체크 진행
			                checkBusinessNumberDuplicate(reg_num, businessMsg, authButton);
			            } else {
			                // 유효하지 않은 사업자번호
			                console.log("fail");
			                businessMsg.css("color", "red").text(result.data[0]["tax_type"]);
			                authButton.prop("disabled", true); // 인증번호 버튼 비활성화
			            }
			        },
			        error: function (result) {
			            console.log("error");
			            console.log(result.responseText);
			            businessMsg.css("color", "red").text("사업자번호 확인 중 오류가 발생했습니다.");
			            authButton.prop("disabled", true); // 인증번호 버튼 비활성화
			        }
			    });
			}

			// 사업자번호 중복 체크 (서버 API 호출)
			function checkBusinessNumberDuplicate(reg_num, businessMsg, authButton) {
			    $.ajax({
			        url: "/member/checkBusinessNo/" + reg_num, // 중복 확인 
			        type: "GET",
			        success: function (response) {
			            if (response) {
			                // 중복된 사업자번호 (이미 가입된 사업자)
			                businessMsg.css("color", "red").text("이미 등록된 사업자번호입니다.");
			                authButton.prop("disabled", true); // 인증번호 버튼 비활성화
			            } else {
			                // 사용 가능한 사업자번호
			                businessMsg.css("color", "green").text("확인되었습니다.");
			                authButton.prop("disabled", false); // 인증번호 버튼 활성화
			            }
			        },
			        error: function () {
			            businessMsg.css("color", "red").text("사업자번호 확인 중 오류가 발생했습니다.");
			            authButton.prop("disabled", true); // 인증번호 버튼 비활성화
			        }
			    });
			}
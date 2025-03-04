document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('.loginup-form');
    const idInput = document.getElementById('userid');
    const passwordInput = document.getElementById('password');
    const passwordConfirmInput = document.getElementById('password-confirm');
    const emailInput = document.getElementById('email');
    const phoneInput = document.getElementById('phone');
    const addressInput = document.getElementById('address');
    const postcodeInput = document.getElementById('postcode');
    const birthdateInput = document.getElementById('birthdate');
    const nameInput = document.getElementById('username');
    const authInput = document.getElementById('phone-auth');
    const authButton = document.querySelector('.phone-btn');
    const submitButton = document.querySelector('.loginup-btn');

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

    let generatedAuthCode = '';

    submitButton.disabled = true;
    authInput.style.display = 'none';

    authButton.addEventListener('click', () => {
        if (validateForm()) {
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
                generatedAuthCode = String(Math.floor(100000 + Math.random() * 900000));
                alert(`인증번호: ${generatedAuthCode}`);
                authInput.style.display = 'block';
                authButton.textContent = '인증번호 확인';
                submitButton.style.display = 'none';
            } else if (authButton.textContent === '인증번호 확인') {
                if (authInput.value === generatedAuthCode) {
                    authMsg.textContent = '';
                    submitButton.disabled = false;
                    submitButton.style.display = 'block';
                    authInput.style.display = 'none';
                    authButton.style.display = 'none';
                    alert('인증이 완료되었습니다.');
                } else {
                    authMsg.textContent = '인증번호가 일치하지 않습니다. 다시 시도해주세요.';
                }
            }
        }
    });

    function validateForm() {
        let isValid = true;
        
        if (idInput.value.trim() === '') {
            idMsg.textContent = '* 아이디는 필수 입력 항목입니다.';
            isValid = false;
        }else if(!/^[a-zA-Z0-9]{5,20}$/.test(idInput.value)) {
            idMsg.textContent = '아이디는 영문자와 숫자 조합으로 5~20자여야 합니다.';
            isValid = false;
        } else {
            idMsg.textContent = '';
        }

        if (passwordInput.value.trim() === '') {
            passwordMsg.textContent = '* 비밀번호는 필수 입력 항목입니다.';
            isValid = false;
        }else if (!/^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/.test(passwordInput.value)) {
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
        }else if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(emailInput.value)) {
            emailMsg.textContent = '올바른 이메일 형식을 입력해주세요.';
            isValid = false;
        } else {
            emailMsg.textContent = '';
        }

        if (phoneInput.value.trim() === '') {
            phoneMsg.textContent = '* 휴대폰 번호는 필수 입력 항목입니다.';
            isValid = false;
        }else if (!/^010\d{4}\d{4}$/.test(phoneInput.value)) {
            phoneMsg.textContent = '휴대폰 번호는 숫자만 입력 가능합니다.';
            isValid = false;
        } else {
            phoneMsg.textContent = '';
        }

        if (postcodeInput.value.trim() === '') {
            postcodeMsg.textContent = '* 우편번호는 필수 입력 항목입니다.';
            isValid = false;
        }else if(!/^\d{5}$/.test(postcodeInput.value)) {
            postcodeMsg.textContent = '우편번호는 5자리여야 합니다.';
            isValid = false;
        } else {
            postcodeMsg.textContent = '';
        }

        if (addressInput.value.trim() === '') {
            addressMsg.textContent = '* 주소는 필수 입력 항목입니다.';
            isValid = false;
        }else if (!/^[가-힣0-9-?\s]{2,20}$/.test(addressInput.value)) {
            addressMsg.textContent = '주소는 한글이랑 숫자만 입력 가능합니다.';
            isValid = false;
        } else {
            addressMsg.textContent = '';
        }

        if (birthdateInput.value.trim() === '') {
            birthdayMsg.textContent = '* 생년월일은 필수 입력 항목입니다.';
            isValid = false;
        }else if (!birthdateInput.value) {
            birthdayMsg.textContent = '생년월일을 입력해주세요.';
            isValid = false;
        } else {
            birthdayMsg.textContent = '';
        }

        if (nameInput.value.trim() === '') {
            nameMsg.textContent = '* 이름은 필수 입력 항목입니다.';
            isValid = false;
        }else if (!/^[가-힣]{2,10}$/.test(nameInput.value)) {
            nameMsg.textContent = '이름은 한글만 가능하며 2자 이상, 10자 이하여야 합니다.';
            isValid = false;
        } else {
            nameMsg.textContent = '';
        }

        return isValid;
    }

    submitButton.addEventListener('click', () => {
        if (validateForm()) {
            alert('회원가입이 완료되었습니다.');
            form.submit();
            window.location.href = 'C:/dev/front_work_space/Project/loginin.html'
        }
    });
});


document.addEventListener('DOMContentLoaded', () => {
    const idBtn = document.querySelector('.find-id-btn');
    const pwBtn = document.querySelector('.find-pw-btn');
    const idInput = document.getElementById('userid');
    const idEmailInput = document.getElementById('id-email');
    const pwEmailInput = document.getElementById('pw-email');
    const nameInput = document.getElementById('username');
    
    const idMsg = document.getElementById('id-msg');
    const idEmailMsg = document.getElementById('id-email-msg');
    const pwEmailMsg = document.getElementById('pw-email-msg');
    const nameMsg = document.getElementById('name-msg');

    idBtn.addEventListener('click', () => {
        if (validateForm1()) {
            alert('아이디 찾기 성공');
        }
    });

    pwBtn.addEventListener('click', () => {
        if (validateForm2()) {
            alert('비밀번호 찾기 성공');
        }
    });

    function validateForm1() {
        let isValid = true;

    if (nameInput.value.trim() === '') {
        nameMsg.textContent = '* 이름은 필수 입력 항목입니다.';
        isValid = false;
    }else if (!/^[가-힣]{2,10}$/.test(nameInput.value)) {
        nameMsg.textContent = '이름은 한글만 가능하며 2자 이상, 10자 이하여야 합니다.';
        isValid = false;
    } else {
        nameMsg.textContent = '';
    }

    if (idEmailInput.value.trim() === '') {
        idEmailMsg.textContent = '* 이메일은 필수 입력 항목입니다.';
        isValid = false;
    } else if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(idEmailInput.value)) {
        idEmailMsg.textContent = '이메일 형식이 올바르지 않습니다.';
        isValid = false;
    } else {
        idEmailMsg.textContent = '';
    }

    return isValid;
    }

    function validateForm2() {
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

    if (pwEmailInput.value.trim() === '') {
        pwEmailMsg.textContent = '* 이메일은 필수 입력 항목입니다.';
        isValid = false;
    } else if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(pwEmailInput.value)) {
        pwEmailMsg.textContent = '이메일 형식이 올바르지 않습니다.';
        isValid = false;
    } else {
        pwEmailMsg.textContent = '';
    }

    return isValid;
    }
    
});
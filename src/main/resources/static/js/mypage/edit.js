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
        nowMsg.textContent = '비밀번호는 영문자, 숫자, 특수문자를 포함해 8~20자여야 합니다.';
        isPass = false;
    } else {
        nowMsg.textContent = '';
    }

    // 새 비밀번호
    if (passwordChange.value.trim() === '') {
        newMsg.textContent = '사용하실 비밀번호를 입력해 주세요';
        isPass = false;
    }else if (!/^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/.test(passwordChange.value)) {
        newMsg.textContent = '비밀번호는 영문자, 숫자, 특수문자를 포함해 8~20자여야 합니다.';
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

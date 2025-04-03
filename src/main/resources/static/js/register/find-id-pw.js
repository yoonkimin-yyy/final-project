document.addEventListener('DOMContentLoaded', function() {
    
    const findIdForm = document.getElementById('find-id-form');
    const usernameInput = document.getElementById('username');
    const idEmailInput = document.getElementById('id-email');
    const nameMsg = document.getElementById('name-msg');
    const idEmailMsg = document.getElementById('id-email-msg');
    const findIdBtn = document.querySelector('.find-id-btn');

    
    const findPwForm = document.getElementById('find-pw-form');
    const userIdInput = document.getElementById('userid');
    const pwEmailInput = document.getElementById('pw-email');
    const idMsg = document.getElementById('id-msg');
    const pwEmailMsg = document.getElementById('pw-email-msg');
    const findPwBtn = document.querySelector('.find-pw-btn');

    
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    const idRegex = /^(?=.*[a-z])(?=.*\d)[a-z0-9]{5,20}$/;
    const nameRegex = /^[가-힣]{2,10}$/;

    
    findIdBtn.addEventListener('click', function() {
        let isValid = true;
        
        
        nameMsg.textContent = '';
        idEmailMsg.textContent = '';
        
        
        const name = usernameInput.value.trim();
        if (name === '') {
            nameMsg.textContent = '* 이름은 필수 입력 항목입니다.';
            nameMsg.style.color = 'red';
            isValid = false;
        } else if (!nameRegex.test(name)) {
            nameMsg.textContent = '이름은 한글만 가능하며 2자 이상, 10자 이하여야 합니다.';
            nameMsg.style.color = 'red';
            isValid = false;
        } else {
            nameMsg.textContent = '올바른 이름 형식입니다.';
            nameMsg.style.color = 'green';
        }
        
        
        const email = idEmailInput.value.trim();
        if (email === '') {
            idEmailMsg.textContent = '* 이메일은 필수 입력 항목입니다.';
            idEmailMsg.style.color = 'red';
            isValid = false;
        } else if (!emailRegex.test(email)) {
            idEmailMsg.textContent = '유효한 이메일 주소를 입력해주세요.';
            idEmailMsg.style.color = 'red';
            isValid = false;
        } else {
            idEmailMsg.textContent = '올바른 이메일 형식입니다.';
            idEmailMsg.style.color = 'green';
        }
        
        
		if (isValid) {
		    $.ajax({
		        url: findIdForm.action,
		        method: 'POST',
		        data: {
		            userEmail: email,
		            userName: name
		        },
		        success: function(response) {
		            if (response === "등록된 정보가 아닙니다.") {
		                alert('등록된 정보가 아닙니다.');
		            } else {
		                alert('아이디 찾기 요청이 완료되었습니다. 로그인 페이지로 이동합니다.');
		                window.location.href = '/member/loginin/form';
		            }
		        },
		        error: function(xhr, status, error) {
		            if (xhr.status === 404) {
		                alert('등록된 정보가 아닙니다.');
		            } else {
		                alert('서버 오류가 발생했습니다.');
		            }
		        }
		    });
		}
    });
    
    
    findPwBtn.addEventListener('click', function() {
        let isValid = true;
        
        
        idMsg.textContent = '';
        pwEmailMsg.textContent = '';
        
        
        const getId = userIdInput.value.trim();
        if (getId === '') {
            idMsg.textContent = '* 아이디는 필수 입력 항목입니다.';
            idMsg.style.color = 'red';
            isValid = false;
        } else if (!idRegex.test(getId)) {
            idMsg.textContent = '아이디는 영문 소문자와 숫자, 5~20자로 입력해야 합니다.';
            idMsg.style.color = 'red';
            isValid = false;
        } else {
            idMsg.textContent = '올바른 아이디 형식입니다.';
            idMsg.style.color = 'green';
        }
        
        
        const email = pwEmailInput.value.trim();
        if (email === '') {
            pwEmailMsg.textContent = '* 이메일은 필수 입력 항목입니다.';
            pwEmailMsg.style.color = 'red';
            isValid = false;
        } else if (!emailRegex.test(email)) {
            pwEmailMsg.textContent = '유효한 이메일 주소를 입력해주세요.';
            pwEmailMsg.style.color = 'red';
            isValid = false;
        } else {
            pwEmailMsg.textContent = '올바른 이메일 형식입니다.';
            pwEmailMsg.style.color = 'green';
        }
        
        
		if (isValid) {
				    $.ajax({
				        url: findPwForm.action,
				        method: 'POST',
				        data: {
							userEmail: email,
							userId: getId
				        },
				        success: function(response) {
				            if (response === "등록된 정보가 아닙니다.") {
				                alert('등록된 정보가 아닙니다.');
				            } else {
				                alert('비밀번호 찾기 요청이 완료되었습니다. 로그인 페이지로 이동합니다.');
				                window.location.href = '/member/loginin/form';
				            }
				        },
				        error: function(xhr, status, error) {
				            if (xhr.status === 404) {
				                alert('등록된 정보가 아닙니다.');
				            } else {
				                alert('서버 오류가 발생했습니다.');
				            }
				        }
				    });
				}
    });

    
    usernameInput.addEventListener('input', function() {
        const name = usernameInput.value.trim();
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
    });
    

    idEmailInput.addEventListener('input', function() {
        const email = idEmailInput.value.trim();
        if (email === '') {
            idEmailMsg.textContent = '* 이메일은 필수 입력 항목입니다.';
            idEmailMsg.style.color = 'red';
        } else if (!emailRegex.test(email)) {
            idEmailMsg.textContent = '유효한 이메일 주소를 입력해주세요.';
            idEmailMsg.style.color = 'red';
        } else {
            idEmailMsg.textContent = '올바른 이메일 형식입니다.';
            idEmailMsg.style.color = 'green';
        }
    });
    
    userIdInput.addEventListener('input', function() {
        const getId = userIdInput.value.trim();
        if (getId === '') {
            idMsg.textContent = '* 아이디는 필수 입력 항목입니다.';
            idMsg.style.color = 'red';
        } else if (!idRegex.test(getId)) {
            idMsg.textContent = '아이디는 영문 소문자와 숫자, 5~20자로 입력해야 합니다.';
            idMsg.style.color = 'red';
        } else {
            idMsg.textContent = '올바른 아이디 형식입니다.';
            idMsg.style.color = 'green';
        }
    });
    
    
    pwEmailInput.addEventListener('input', function() {
        const email = pwEmailInput.value.trim();
        if (email === '') {
            pwEmailMsg.textContent = '* 이메일은 필수 입력 항목입니다.';
            pwEmailMsg.style.color = 'red';
        } else if (!emailRegex.test(email)) {
            pwEmailMsg.textContent = '유효한 이메일 주소를 입력해주세요.';
            pwEmailMsg.style.color = 'red';
        } else {
            pwEmailMsg.textContent = '올바른 이메일 형식입니다.';
            pwEmailMsg.style.color = 'green';
        }
    });
});
    const typeUser = document.querySelector('.type-user');
    const typeBusiness = document.querySelector('.type-business');
    const typeBtn = document.querySelectorAll('.type-btn');

    typeBtn.forEach((btn) => {
        btn.addEventListener('click', () => {
            if (btn.textContent === '일반 회원가입') {
                location.href = './checkbox.html';
            } else {
                location.href = './checkbox.html';
            }
        });
    });

document.addEventListener("DOMContentLoaded", function () {
    const userButton = document.getElementById('loginup-btn');
    const businessButton = document.getElementById('businessloginup-btn');

		// 일반
        userButton.addEventListener('click', function () {
            location.href = '/register/checkbox?type=user';
        });
   		
		// 사업자
        businessButton.addEventListener('click', function () {
            location.href = '/register/checkbox?type=business';
        });

});

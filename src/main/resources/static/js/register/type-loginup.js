document.addEventListener("DOMContentLoaded", function () {
    const userButton = document.getElementById('loginup-btn');
    const businessButton = document.getElementById('businessloginup-btn');

});
function typeUser() {
	const userButton = document.getElementById('loginup-btn');
	location.href = '/member/checkbox?type=user'
}
function typeBusiness() {
	const businessButton = document.getElementById('businessloginup-btn');
	location.href = '/member/checkbox?type=business'
}
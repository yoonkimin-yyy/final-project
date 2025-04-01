document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("reportForm").addEventListener("submit", function (event) {
        event.preventDefault(); // 기본 폼 제출 막기

        const formData = new FormData(this);
        fetch(this.action, {
            method: this.method,
            body: formData
        }).then(response => {
            if (response.ok) {
				alert("리뷰 신고가 제출되었습니다.");
                setTimeout(() => {
                    window.close();
                }, 500);
            }
        }).catch(error => console.error("폼 제출 오류:", error));
    });
});
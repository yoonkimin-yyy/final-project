document.addEventListener("DOMContentLoaded", function () {
        const form = document.getElementById("insertForm");
        form.onsubmit = function (event) {
            event.preventDefault(); // 기본 제출 방지

            // FormData 객체 생성
            const formData = new FormData(form);

            fetch(form.action, {
                method: form.method,
                body: formData
            }).then(response => {
                if (response.ok) {
                    window.opener.location.reload(); // 부모창 새로고침
                    window.close(); // 현재 창 닫기
                } else {
                    alert("메뉴 추가에 실패했습니다.");
                }
            }).catch(error => console.error("Error:", error));
        };
    });
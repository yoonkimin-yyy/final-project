document.getElementById("insertForm").addEventListener('submit', function (event) {
        event.preventDefault(); // 기본 제출 방지

        const formData = new FormData(this);

        fetch(this.action, {
            method: this.method,
            body: formData
        })
        .then(response => response.json()) // JSON 응답 처리
        .then(data => {
            if (data.message === "success") {
                window.close(); // 서버가 정상적으로 처리했을 때 창 닫기
            } else {
                alert("메뉴 추가 실패");
            }
        })
        .catch(error => {console.error("오류 발생:", error)});
    });
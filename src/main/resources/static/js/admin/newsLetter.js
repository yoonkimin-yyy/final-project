function sendNewsletter() {
        fetch("/bbanggil/send", {
            method: "POST",
            headers: { "Content-Type": "application/json" }
        })
        .then(response => response.text())
        .then(data => {
            alert(data);
        })
        .catch(error => {
            console.error(" 뉴스레터 발송 오류:", error);
            alert(" 뉴스레터 발송 중 오류가 발생했습니다.");
        });
    }
   
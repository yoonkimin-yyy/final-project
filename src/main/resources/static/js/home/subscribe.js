function subscribe() {
    let email = document.getElementById("email").value;
    let message = document.getElementById("subscribed-message");

    if (!email) {
        alert(" 이메일을 입력해주세요!");
        return;
    }

    
    let formData = new URLSearchParams();
    formData.append("email", email);

    fetch("http://localhost:8080/bbanggil/subscribe", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"  
        },
        body: formData  //  FormData 전송
    })
    .then(response => response.text())
    .then(data => {
        message.style.display = "block";
        message.innerText = data;
        document.getElementById("email").value = ""; 
    })
    .catch(error => {
        console.error(" 오류 발생:", error);
        alert(" 구독 요청 중 오류가 발생했습니다.");
    });
}
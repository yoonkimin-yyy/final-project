function subscribe() {
    let email = document.getElementById("email").value;
    let message = document.getElementById("subscribed-message");

    if (!email) {
        alert("이메일을 입력해주세요!");
        return;
    }

    // ✅ JSON 형식으로 데이터 변환
    let formData = JSON.stringify({ email: email });

    fetch("/subscribe", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"  // JSON으로 전송
        },
        body: formData  //  JSON 형식 데이터 전송
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.text();
    })
    .then(data => {
        message.style.display = "block";
        message.innerText = data;
        document.getElementById("email").value = "";
    })
    .catch(error => {
        console.error("오류 발생:", error);
        alert("구독 요청 중 오류가 발생했습니다.");
    });
}
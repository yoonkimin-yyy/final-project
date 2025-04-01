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
					const bakeryNo = formData.get("bakeryNo");
					
                    window.opener.location.href = "/bakery/menu/list/form?no="+bakeryNo; // 부모창 새로고침
                    window.close(); // 현재 창 닫기
                } else {
                    alert("메뉴 추가에 실패했습니다.");
                }
            }).catch(error => console.error("Error:", error));
        };
		
		const fileInput = document.getElementById("imageUpload");
			    const previewImage = document.getElementById("imagePreview");

			    fileInput.addEventListener("change", function () {
			        handleFileChange(this);
			    });

			    function handleFileChange(inputElement) {
			        const file = inputElement.files[0]; // 하나의 파일만 선택 가능
			        if (!file) return;

			        const reader = new FileReader();
			        reader.onload = function (e) {
			            previewImage.src = e.target.result;
			            previewImage.style.display = "block"; // 이미지 표시
			        };
			        reader.readAsDataURL(file);
			    }
    });
function submitReview() {
    function getSelectedTags() {
        return Array.from(document.querySelectorAll(".tag-checkbox:checked")).map(tag => {
            const label = document.querySelector(`label[for="${tag.id}"]`);
            return label ? label.outerHTML.trim() : "";
        });
    }
	const userNo = document.getElementById("userNum").value; 
	console.log(userNo);
	console.log("sdfsfsfs");
	if (!userNo || userNo === "null") {
	       alert("로그인이 필요합니다. 로그인 후 리뷰를 작성해주세요.");
		   window.location.href = window.location.origin + "/register/loginin/form";
	       return;
	   }
	
	
    const name = document.getElementById("userId").value;
    const rating = document.getElementById("rating").value;
    const content = document.getElementById("content").value;
    const date = new Date().toISOString().split("T")[0];
	const bakeryNo = document.getElementById("bakeryNo").value; 
	
    const selectedTags = getSelectedTags();
    const files = document.getElementById("imageInput").files;

    let formData = new FormData();
	
	
   
	
	const reviewData = {
	    userNo: userNo,
	    bakeryNo: bakeryNo,
	    reviewDetail: content,
	    reviewRating: rating,
	    reviewTag: selectedTags, //  태그 리스트
	    writeDate: date
	};
	
	formData.append("reviewDto", new Blob([JSON.stringify(reviewData)], { type: "application/json" }));
	// 이미지 추가 
	if (files.length > 0) {
	    for (let i = 0; i < files.length; i++) {
	        formData.append("reviewImage", files[i]); //  여러 개의 파일 추가
	    }
	}
	
	//  FormData 디버깅 (콘솔 확인)
	  console.log(" FormData 내용 확인:");
	  for (let pair of formData.entries()) {
	      console.log(pair[0], pair[1]);
	  }

    fetch("/review/write", {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("서버 응답 오류");
        }
        return response.text();
    })
    .then(data => {
        alert("리뷰가 등록되었습니다!");
        closeReviewModal();
        document.getElementById("reviewForm").reset();
        document.getElementById("preview").innerHTML = '';
    })
    .catch(error => {
        alert("리뷰 작성 실패: " + error.message);
    });
}



function fetchBakeryData(bakeryNo, callback) {
    if (!bakeryNo) {
        alert("빵집 번호가 없습니다.");
        return;
    }

    bakeryNo = parseFloat(bakeryNo); // String → double 변환
	console.log(bakeryNo);

    fetch(`/bakery/kakao?bakeryNo=${bakeryNo}`)  // Query Parameter 방식
        .then(response => {
            if (!response.ok) {
                throw new Error("빵집 정보를 찾을 수 없습니다.");
            }
            return response.json();
        })
        .then(bakery => {
            callback(bakery); // 데이터를 받아서 callback 함수 실행
        })
        .catch(error => {
            console.error("빵집 정보 로드 실패:", error);
            alert("빵집 정보를 불러올 수 없습니다.");
        });
}




function submitReviewEdit() {
    //  리뷰 수정 데이터 생성
	
    const formData = new FormData();
    formData.append("reviewNo", document.getElementById("reviewNo").value);
    formData.append("reviewDetail", document.getElementById("content").value);
    formData.append("reviewRating", document.getElementById("rating").value);

	
	
    //  선택된 태그 추가
    const selectedTags = [];
    document.querySelectorAll(".tag-checkbox:checked").forEach(tag => {
        selectedTags.push(tag.id);
    });
    formData.append("tags", selectedTags.join(","));

    //  이미지 추가 (파일 업로드)
    const imageInput = document.getElementById("imageInput");
    if (imageInput.files.length > 0) {
        for (let i = 0; i < imageInput.files.length; i++) {
            formData.append("reviewImage", imageInput.files[i]);
        }
    }

    //  AJAX를 이용한 POST 요청 (리뷰 수정)
    fetch("/review/edit", {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (response.ok) {
            alert("리뷰가 수정되었습니다!");
            location.reload(); // 수정 후 새로고침
        } else {
            alert("리뷰 수정에 실패했습니다.");
        }
    })
    .catch(error => console.error("리뷰 수정 요청 실패", error));

}



function deleteReview(reviewNo, fileName) {
    if (!confirm("정말 이 리뷰를 삭제하시겠습니까?")) {
        return;
    }
	
	

    const formData = new FormData();
    formData.append("reviewNo", reviewNo);
    formData.append("fileName", fileName ? fileName:"none");
	
	console.log(reviewNo);
	console.log(fileName);
	

    fetch("/review/delete", {
        method: "POST",
        body: formData
    })
    .then(response => response.json())  // JSON 응답을 받음
    .then(data => {
        if (data.success) {
            alert("리뷰가 삭제되었습니다!");
            location.reload(); //  삭제 후 페이지 새로고침
        } else {
            alert("리뷰 삭제에 실패했습니다.");
        }
    })
    .catch(error => console.error("리뷰 삭제 실패", error));
}















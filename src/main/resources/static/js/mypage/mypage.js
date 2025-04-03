
function toggleDetail(clickedRow) {
    const nextRow = clickedRow.nextElementSibling;
	// 특정 class를 포함하면 함수가 실행되지 않음
	if (event.target.classList.contains('no-toggle')) {
	       return;
	   }
	 
    // 상세 정보가 표시되는 <tr>인지 확인
    if (nextRow && nextRow.classList.contains('detail-row')) {
        const detailDiv = nextRow.querySelector('.detail-info');

        // 현재 상세 내용이 보이면 숨김
        if (nextRow.style.display === 'table-row') {
            nextRow.style.display = 'none';
        } else {
            // 다른 상세 내용은 닫지 않고, 현재 선택된 것만 토글
            nextRow.style.display = 'table-row';
        }
    }
}

// 작성한 리뷰 삭제 
document.addEventListener('DOMContentLoaded', () => {
    const deleteButton = document.querySelector('.delete-btn');
    const reviewInfoDiv = document.querySelector('.review-info-div');
    const reviewWriteDiv = document.querySelector('.review-div');

    deleteButton.addEventListener('click', () => {
        const isConfirmed = confirm('정말 리뷰를 삭제하시겠습니까?');

        if (isConfirmed) {
            reviewInfoDiv.style.display = 'none';
            reviewWriteDiv.style.display = 'block';
            alert("삭제 되었습니다.")
			

            // 리뷰 작성 화면 초기화
            resetReviewForm();
            console.log('리뷰가 삭제되었고, 리뷰 작성 화면으로 전환되었습니다.');
        } 
    });
});

// 리뷰 작성 하기 버튼 (등록 버튼 아님)
function writeBtn(index) {
	console.log(index)
    const textDiv = document.getElementById(`review-text-div-${index}`);
    
    if (textDiv.style.display === 'none' || textDiv.style.display === '') {
        textDiv.style.display = 'flex'; // 보이게 함
    } else {
        textDiv.style.display = 'none'; // 숨김
    }
}

// 리뷰 작성 ~ 등록 버튼 까지

	




    const submitButton = document.querySelector('.text-btn');
    const reviewTextarea = document.querySelector('.review-text-textarea');
    const charCount = document.querySelector('.char-count');
    let selectedRating = 0;

    const deleteButton = document.querySelector('.delete-btn');
    const reviewTextOutput = document.querySelector('.review-info-div .review-text p');
 

    const tagInputs = document.querySelectorAll('.review-div .tag');
    const tagOutputsContainer = document.querySelector('.review-info-div .tag-container');

	
    // 글자 수 실시간 업데이트
    reviewTextarea.addEventListener('input', () => {
        const currentLength = reviewTextarea.value.length;
        charCount.textContent = `${currentLength}/100`;
    });



	// ⭐ 별점 클릭 이벤트 처리
	document.querySelectorAll(".stars").forEach((starsContainer, formIndex) => {
	    const stars = starsContainer.querySelectorAll("i"); // 현재 별 그룹의 모든 별 가져오기
	    let selectedRating = 0; // 초기 별점 값

	    stars.forEach((star, starIndex) => {
	        star.addEventListener("click", () => {
	            let ratingCount = starIndex + 1; // 별은 1~5점

	            // ⭐ 클릭 로직 개선: 클릭할 때 반쪽 별도 적용 가능하도록
	            if (selectedRating === ratingCount - 0.5) {
	                selectedRating = ratingCount; // 반쪽 별 → 꽉 찬 별
	            } else if (selectedRating === ratingCount) {
	                selectedRating = 0; // 다시 클릭하면 초기화
	            } else {
	                selectedRating = ratingCount - 0.5; // 반쪽 별 적용
	            }

	            // ⭐ 별 업데이트 함수 호출
	            fillStars(stars, selectedRating);

	            // 해당 리뷰 폼의 input 태그에 값 저장
	            let ratingInput = document.getElementById(`review-rating-${formIndex}`);
	            if (ratingInput) {
	                ratingInput.value = selectedRating;
	                console.log(`review-rating-${formIndex} 값 설정됨:`, ratingInput.value);
	            }
	        });
	    });
	});

	// ⭐ 페이지 로드 시 저장된 별점 반영
	document.querySelectorAll(".stars").forEach((starContainer, index) => {
	    const ratingValueElement = document.getElementById(`rating-value-${index}`); // 해당 컨테이너의 rating-value 가져오기
	    if (!ratingValueElement) return;

	    const rating = parseFloat(ratingValueElement.textContent); // 해당 리스트의 저장된 별점 가져오기
	    const stars = starContainer.querySelectorAll("i");

	    fillStars(stars, rating); // 저장된 별점 값 반영
	});
	

	// ⭐ 별점 UI 업데이트 함수
	function fillStars(stars, selectedRating) {
	    stars.forEach((star, i) => {
	        star.classList.remove('fas', 'fa-star', 'fa-star-half-alt', 'far');

	        if (selectedRating === 0) {
	            star.classList.add('far', 'fa-star'); // 빈 별
	        } else if (i < Math.floor(selectedRating)) {
	            star.classList.add('fas', 'fa-star'); // 꽉 찬 별
	        } else if (i === Math.floor(selectedRating) && selectedRating % 1 !== 0) {
	            star.classList.add('fas', 'fa-star-half-alt'); // 반쪽 별
	        } else {
	            star.classList.add('far', 'fa-star'); // 빈 별
	        }
	    });
	}


	
   






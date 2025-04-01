
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
document.addEventListener('DOMContentLoaded', () => {
	




    const submitButton = document.querySelector('.text-btn');
    const reviewTextarea = document.querySelector('.review-text-textarea');
    const charCount = document.querySelector('.char-count');
    const stars = document.querySelectorAll('.review-div .stars i');
    let selectedRating = 0;

    const deleteButton = document.querySelector('.delete-btn');
    const reviewTextOutput = document.querySelector('.review-info-div .review-text p');
    const starsOutput = document.querySelectorAll('.review-info-div .stars i');

    const tagInputs = document.querySelectorAll('.review-div .tag');
    const tagOutputsContainer = document.querySelector('.review-info-div .tag-container');

	
    // 글자 수 실시간 업데이트
    reviewTextarea.addEventListener('input', () => {
        const currentLength = reviewTextarea.value.length;
        charCount.textContent = `${currentLength}/100`;
    });

    // 등록 버튼 클릭 시 처리
    /*submitButton.addEventListener('click', () => {
        const reviewText = reviewTextarea.value.trim();
        let errorMessage = '';

        if (reviewText === '' && selectedRating === 0) {
            errorMessage = '평점과 내용을 작성해 주세요';
        } else if (reviewText === '') {
            errorMessage = '내용을 입력해 주세요';
        } else if (selectedRating === 0) {
            errorMessage = '평점을 선택해 주세요.';
        } 

        if (errorMessage) {
            alert(errorMessage);
            return;
        }

        // 정상 등록 시
        alert("리뷰가 등록되었습니다!");

        // 작성한 내용을 내 리뷰 div에 적용
        reviewTextOutput.textContent = reviewText;

        // 별점 적용
        starsOutput.forEach((star, index) => {
            star.classList.remove('fas', 'fa-star', 'far');
            if (index < selectedRating) {
                star.classList.add('fas', 'fa-star'); // 채워진 별
            } else {
                star.classList.add('far', 'fa-star'); // 빈 별
            }
        });

        // 태그 적용 (선택된 태그만 표시)
        tagOutputsContainer.innerHTML = '';
        tagInputs.forEach(tag => {
            if (tag.classList.contains('active')) {
                const tagClone = tag.cloneNode(true);
                tagClone.classList.remove('active');
                tagOutputsContainer.appendChild(tagClone);
            }
        });

        console.log('리뷰가 등록되었고, 내 리뷰 div 내용이 업데이트되었습니다.');

        // 초기화 작업
        resetReviewForm();
    });*/


    // 별점 클릭 처리
	const starGroups = document.querySelectorAll(".stars"); // 모든 별 그룹 가져오기

	    starGroups.forEach((starsContainer, formIndex) => {
	        const stars = starsContainer.querySelectorAll("i"); // 현재 그룹의 별들 가져오기
	        let selectedRating = 0; // 선택된 별점 값

	        stars.forEach((star, starIndex) => {
	            star.addEventListener("click", () => {
	                let ratingCount = starIndex % 5; // 0~4 값 (각 그룹별 별점 인덱스)

	                if (selectedRating === ratingCount + 0.5) {
	                    selectedRating = ratingCount + 1; // 반 별 → 꽉 찬 별
	                } else if (selectedRating === ratingCount + 1) {
	                    selectedRating = 0; // 초기화
	                } else {
	                    selectedRating = ratingCount + 0.5; // 반 별 선택
	                }

	                fillStars(stars, starIndex); // 별 색상 변경

	                // 해당 리뷰 폼의 input 태그 찾기
	                let ratingInput = document.getElementById(`review-rating-${formIndex}`);
	                if (ratingInput) {
	                    ratingInput.value = selectedRating; // 선택한 평점 값 저장
	                    console.log(`review-rating-${formIndex} 값 설정됨:`, ratingInput.value);
	                } 
	            });
	        });
	    });
   /*stars.forEach((star, index) => {
        star.addEventListener('click', () => {
            if (selectedRating === index + 0.5) {
                selectedRating = index + 1; // 반 별 → 꽉 찬 별
            } else if (selectedRating === index + 1) {
                selectedRating = 0; // 초기화
            } else {
                selectedRating = index + 0.5; // 초기화 또는 다른 경우 반 별
            }
            fillStars(selectedRating);
				console.log(index)
            console.log('현재 선택된 평점:', selectedRating);
			
			// 0 ~ 4
			// 5 ~ 9
			// 10~14
			// 15~19
			// 20~24
			
			const ratingInput = document.getElementById("review-rating");
			ratingInput.value = selectedRating;  // input 태그에 selectedRating 값 설정
			console.log(ratingInput.value)
        });
    });*/

	document.querySelectorAll(".stars").forEach(starContainer => {
	        const rating = document.getElementById("rating-value").textContent; // 별점 값 가져오기
	        const stars = starContainer.querySelectorAll("i"); // 별 태그들 가져오기
			console.log(rating)
	        fillStars(stars, rating);  // 별점 값에 맞춰 채우기
	    });


    // 별점 채우기
	function fillStars(stars, selectedRating) {
	    stars.forEach((star, i) => {
	        star.classList.remove('fas', 'fa-star', 'fa-star-half-alt', 'far');

	        if (selectedRating === 0) {
	            star.classList.add('far', 'fa-star'); // 모든 별 빈 상태
	        } else if (i < selectedRating) {
	            star.classList.add('fas', 'fa-star'); // 꽉 찬 별
	        } else {
	            star.classList.add('far', 'fa-star'); // 빈 별
	        }
	    });
	}



	
   


    // 리뷰 작성 화면 초기화 함수
    function resetReviewForm() {
        // 별점 초기화
        stars.forEach(star => {
            star.classList.remove('fas', 'fa-star', 'fa-star-half-alt');
            star.classList.add('far', 'fa-star'); // 빈 별로 초기화
        });

        // 텍스트 입력 초기화
        reviewTextarea.value = '';
        charCount.textContent = '0/100';

        // 태그 초기화 (선택 해제)
        tags.forEach(tag => {
            tag.classList.remove('active');
        });
    }
});

// 태그 선택 기능
document.addEventListener('DOMContentLoaded', () => {
	const tags = document.querySelectorAll('.tag');
		console.log(tags)
		   tags.forEach(tag => {
		       tag.addEventListener('click', () => {
		           tag.classList.toggle('active');
		       });
		   });
	   });
	   
const reviewDetail = document.getElementById('review-text-textarea').value;
console.log(reviewDetail)


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




// 리뷰 작성 ~ 등록 버튼 까지
document.addEventListener('DOMContentLoaded', () => {
	
// 리뷰 작성 하기 버튼 (등록 버튼 아님)
const writeBtn = document.getElementById('review-write-btn');
const textDiv = document.getElementById('review-text-div');

textDiv.style.display = 'flex';
textDiv.style.display = 'none';

console.log(writeBtn);

writeBtn.addEventListener('click', () => {
        if(textDiv.style.display === 'none') {
            textDiv.style.display = 'flex';
        }else {
            textDiv.style.display = 'none';
        }


})
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
    submitButton.addEventListener('click', () => {
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
    });


    // 별점 클릭 처리
    stars.forEach((star, index) => {
        star.addEventListener('click', () => {
            if (selectedRating === index + 0.5) {
                selectedRating = index + 1; // 반 별 → 꽉 찬 별
            } else if (selectedRating === index + 1) {
                selectedRating = 0; // 초기화
            } else {
                selectedRating = index + 0.5; // 초기화 또는 다른 경우 반 별
            }
            fillStars(selectedRating);
            console.log('현재 선택된 평점:', selectedRating);
        });
    });

    // 별점 채우기
    function fillStars(rating) {
        stars.forEach((star, i) => {
            star.classList.remove('fas', 'fa-star', 'fa-star-half-alt', 'far');
            
            if (i < Math.floor(rating)) {
                star.classList.add('fas', 'fa-star'); // 꽉 찬 별
            } else if (i === Math.floor(rating) && rating % 1 !== 0) {
                star.classList.add('fas', 'fa-star-half-alt'); // 반 별
            } else {
                star.classList.add('far', 'fa-star'); // 빈 별
            }
        });
    }

    // 태그 선택 기능
    const tags = document.querySelectorAll('.tag');
    tags.forEach(tag => {
        tag.addEventListener('click', () => {
            tag.classList.toggle('active');
        });
    });

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

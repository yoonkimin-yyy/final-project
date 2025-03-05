let selectedImages = []; //
document.addEventListener('DOMContentLoaded', () => {

    updateGauge();
    // ===== 탭 전환 기능 =====
    const tabButtons = document.querySelectorAll('.tab-button');
    tabButtons.forEach(button => {
        button.addEventListener('click', () => {
            tabButtons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
            const tab = button.dataset.tab;
            const allTabs = ['menu', 'reviews', 'interior', 'exterior', 'parking'];
            allTabs.forEach(tabId => {
                const tabElement = document.getElementById(tabId);
                if (tabElement) {
                    tabElement.style.display = tab === tabId ? 'block' : 'none';
                }
            });
        });
    });
    
    // 별점 HTML 생성 함수
    function generateStarsHTML(rating) {
        const fullStars = Math.floor(rating);
        const hasHalfStar = rating % 1 !== 0;
        let html = '';
    
        for (let i = 0; i < fullStars; i++) {
            html += '<i class="fas fa-star"></i>';
        }
        if (hasHalfStar) {
            html += '<i class="fas fa-star-half-alt"></i>';
        }
        for (let i = Math.ceil(rating); i < 5; i++) {
            html += '<i class="far fa-star"></i>';
        }
    
        return html;
    }
      // ===== 리뷰 별점 기능 =====
      const starContainer = document.querySelector('.stars');
      const stars = starContainer.querySelectorAll('i');
      const ratingValue = document.querySelector('.rating-value');
      const ratingInput = document.querySelector('#rating');
  
      function updateStars(rating) {
          const fullStars = Math.floor(rating);
          const hasHalfStar = rating % 1 !== 0;
  
          stars.forEach((star, index) => {
              if (index < fullStars) {
                  star.className = 'fas fa-star';
              } else if (hasHalfStar && index === fullStars) {
                  star.className = 'fas fa-star-half-alt';
              } else {
                  star.className = 'far fa-star';
              }
          });
  
          ratingValue.textContent = rating;
          ratingInput.value = rating;
      }

      starContainer.addEventListener('mousemove', (e) => {
        const starWidth = stars[0].offsetWidth;
        const rect = starContainer.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const starIndex = Math.floor(x / starWidth);
        const starPercent = (x % starWidth) / starWidth;

        let rating;
        if (starPercent < 0.5) {
            rating = starIndex + 0.5;
        } else {
            rating = starIndex + 1;
        }

        rating = Math.max(0.5, Math.min(5, rating));
        updateStars(rating);
    });

    starContainer.addEventListener('click', (e) => {
        const starWidth = stars[0].offsetWidth;
        const rect = starContainer.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const starIndex = Math.floor(x / starWidth);
        const starPercent = (x % starWidth) / starWidth;

        let rating;
        if (starPercent < 0.5) {
            rating = starIndex + 0.5;
        } else {
            rating = starIndex + 1;
        }

        rating = Math.max(0.5, Math.min(5, rating));
        updateStars(rating);
    });

    starContainer.addEventListener('mouseleave', () => {
        const currentRating = parseFloat(ratingInput.value) || 0;
        updateStars(currentRating);
    });



// 모달 제어 함수
function openReviewModal() {
    document.getElementById('reviewModal').style.display = 'block';
    document.body.style.overflow = 'hidden'; // 배경 스크롤 방지
}

function closeReviewModal() {
    document.getElementById('reviewModal').style.display = 'none';
    document.body.style.overflow = 'auto'; // 배경 스크롤 복원

}
// ===== 리뷰 작성 버튼 클릭 이벤트 =====
document.getElementById('openReviewModal')?.addEventListener('click', () => {
    openReviewModal();
});





const reviewForm = document.getElementById('reviewForm');
// 이미지 업로드 관련 요소들 가져오기
const imageInput = document.getElementById('imageInput');
const imageUploadBtn = document.querySelector('.image-upload-btn');
const imagePreviewContainer = document.querySelector('.image-preview-container');
let selectedImages = [];




imageUploadBtn.addEventListener('click', () => {
    imageInput.click();
});

imageInput.addEventListener('change', (e) => {
    const files = Array.from(e.target.files);
    files.forEach(file => {
        if (file.type.startsWith('image/')) {
            const reader = new FileReader();
            reader.onload = (e) => {
                const imageUrl = e.target.result;
                selectedImages.push(imageUrl);
                addImagePreview(imageUrl);
            };
            reader.readAsDataURL(file);
        }
    });
    imageInput.value = ''; // 리셋하여 같은 파일 다시 선택 가능하게
});

function addImagePreview(imageUrl) {
    const previewItem = document.createElement('div');
    previewItem.className = 'image-preview-item';
    previewItem.innerHTML = `
        <img src="${imageUrl}" alt="Preview">
        <button class="remove-image" title="이미지 삭제">×</button>
    `;

    previewItem.querySelector('.remove-image').addEventListener('click', () => {
        const index = selectedImages.indexOf(imageUrl);
        if (index > -1) {
            selectedImages.splice(index, 1);
        }
        previewItem.remove();
    });

    imagePreviewContainer.appendChild(previewItem);
}
function updateTagCountsAndGauge(selectedTags) {
    const tagElements = document.querySelectorAll('.tag');

    // ✅ 선택된 태그 개수 증가
    selectedTags.forEach(tagHTML => {
        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = tagHTML;

        // ✅ 태그 텍스트만 추출 (이모지 제외)
        const tagText = tempDiv.querySelector('.tag-text')?.textContent.trim();
        if (!tagText) return; // 유효하지 않은 태그 무시

        // ✅ 기존 태그 중복 체크
        let tagElement = Array.from(tagElements).find(tag =>
            tag.querySelector('.text').textContent.trim() === tagText
        );

        if (tagElement) {
            // ✅ 기존 태그 개수 증가
            let countElement = tagElement.querySelector('.count');
            countElement.textContent = parseInt(countElement.textContent, 10) + 1;
        } else {
            // ✅ 태그가 없으면 새로 추가
            const tagContainer = document.querySelector('.tag-container');
            const newTag = document.createElement('div');
            newTag.classList.add('tag');
            newTag.innerHTML = `
                <div class="tag-header">
                    <div class="tag-left">
                        <span class="text">${tagText}</span>
                    </div>
                    <span class="count">1</span>
                </div>
                <div class="gauge-container">
                    <div class="gauge"></div>
                </div>
            `;
            tagContainer.appendChild(newTag);
        }
    });

    // ✅ 게이지 업데이트 실행
    updateGauge();
}






reviewForm.addEventListener('submit', (e) => {
    e.preventDefault();

    

    const name = document.getElementById('name').value;
    const rating = document.getElementById('rating').value;
    const content = document.getElementById('content').value;
    const date = new Date().toISOString().split('T')[0];
    const starsHTML = generateStarsHTML(parseFloat(rating));

    const selectedTags = Array.from(document.querySelectorAll('.tag-checkbox:checked')).map(tag => {
        const label = document.querySelector(`label[for="${tag.id}"]`);
        return label.outerHTML.trim(); // ✅ 기존 태그 HTML을 그대로 복사
    });




    // ✅ 태그 HTML 생성
    
    const tagsHTML = selectedTags.length > 0
    ? `<div class="review-tags-display">
        ${selectedTags.join(' ')}  <!-- ✅ 그대로 사용 -->
       </div>`
    : '';



    // 이미지 HTML 생성
    const imagesHTML = selectedImages.length > 0 
        ? `<div class="review-images">
            ${selectedImages.map(url => `
                <img src="${url}" alt="리뷰 이미지" class="review-image">
            `).join('')}
           </div>`
        : '';

        const reviewHTML = `
            <div class="review">
                <div class="review-header">
                <span class="reviewer-name">${name}</span>
                <span class="review-date">${date}</span>
            </div>
            <div class="review-rating" data-rating="${rating}">
                 ${starsHTML}
            </div>
        <div class="review-content">${content}</div>
        ${imagesHTML ? `<div class="review-images">${imagesHTML}</div>` : ""}
        ${tagsHTML ? `<div class="review-tags-display">${tagsHTML}</div>` : ""}
        <div class="review-actions">
            <button class="review-edit-btn">수정</button>
            <button class="review-delete-btn">삭제</button>
        </div>
    </div>
    `;
  
    if (currentEditingReview) {
        console.log("🔄 기존 리뷰 수정 중!");
        console.log(currentEditingReview)
        console.log(currentEditingReview.prevTags)
        console.log(selectedTags)
        // ✅ 기존 리뷰 수정
        const prevTags = currentEditingReview.prevTags || [];
        currentEditingReview.innerHTML = reviewHTML;

       updateTagCountsOnEdit(prevTags, selectedTags);


        //currentEditingReview = null;
    } else {
        // ✅ 새 리뷰 추가
        document.querySelector('.review-list').insertAdjacentHTML('afterbegin', reviewHTML);
        
            updateTagCountsAndGauge(selectedTags);
    }

    updateGauge();

    closeReviewModal();

   
    // 폼 초기화
    reviewForm.reset();
    updateStars(0);
    selectedImages.length = 0;
    document.getElementById("previewImages").innerHTML = '';


    // 현재 정렬 상태 유지
    if (reviewSort) {
        sortReviews(reviewSort.value);
    }
});


document.querySelector('.review-list').addEventListener('click', (e) => {
    if (e.target.classList.contains('review-edit-btn')) {
        const reviewElement = e.target.closest('.review');
        console.log("✅ 클릭된 리뷰 요소:", reviewElement);
        
        if (!reviewElement) {
            console.error("❌ reviewElement가 없습니다! (버튼이 .review 내부에 있는지 확인)");
        } else {
            startEditing(reviewElement);
        }
    } else if (e.target.classList.contains('review-delete-btn')) {
        deleteReview(e.target.closest('.review'));
    }
});




function updateGauge() {
    const tags = document.querySelectorAll('.tag');
    if (tags.length === 0) return;

    // ✅ 모든 태그 개수 가져오기
    const counts = Array.from(tags).map(tag => {
        const countElement = tag.querySelector('.count');
        return countElement ? parseInt(countElement.textContent.trim(), 10) : 0;
    });

    const maxCount = Math.max(...counts, 1); // 최소 1로 설정하여 NaN 방지

    // ✅ 각 태그의 게이지 업데이트
    tags.forEach((tag, index) => {
        const percent = (counts[index] / maxCount) * 100;
        const gaugeElement = tag.querySelector('.gauge');
        if (gaugeElement) {
            gaugeElement.style.width = `${percent}%`;
        }
    });
}




   
 // 리뷰 삭제 함수
 function deleteReview(reviewElement) {
    if (confirm('정말로 이 리뷰를 삭제하시겠습니까?')) {
        console.log(reviewElement);

        // ✅ 삭제되는 리뷰에서 사용된 태그 찾기
        const reviewTagsContainer = reviewElement.querySelector('.review-tags-display');
        if (!reviewTagsContainer) {
            console.warn("⚠️ review-tags-display 요소가 없습니다.");
            return;
        }

        const reviewTags = Array.from(reviewTagsContainer.querySelectorAll('.tag-text'))
            .map(tag => tag.textContent.trim());

        console.log("📝 삭제할 태그 리스트:", reviewTags);
        // ✅ 태그 카운트 감소
        reviewTags.forEach(tagText => {
            const tagElement = Array.from(document.querySelectorAll('.tag')).find(tag =>
                tag.querySelector('.text').textContent.trim() === tagText
            );

            if (tagElement) {
                let countElement = tagElement.querySelector('.count');
                let currentCount = parseInt(countElement.textContent.trim(), 10);
                if (currentCount > 1) {
                    countElement.textContent = currentCount - 1;
                } else {
                    tagElement.remove();
                }
            } else {
                console.warn(`⚠️ 태그를 찾을 수 없습니다: ${tagText}`);
            }
        });

        // ✅ 리뷰 삭제
        reviewElement.remove();
        console.log("✅ 리뷰 삭제 완료!");

        // ✅ 게이지 업데이트
        updateGauge();
    }
}
      


// ESC 키로 모달 닫기
document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
        closeReviewModal();
    }
});

// 모달 바깥 클릭시 닫기
document.getElementById('reviewModal').addEventListener('click', (e) => {
    if (e.target === document.getElementById('reviewModal')) {
        closeReviewModal();
    }
});

function startEditing(reviewElement) {
    console.log("startEditing() 호출됨");
    console.log("🔍 전달된 reviewElement:", reviewElement);


    if (!reviewElement) {
        console.error("❌ reviewElement가 없습니다!");
        return;
    }
    currentEditingReview = reviewElement;

    currentEditingReview.prevTags = Array.from(reviewElement.querySelectorAll('.review-tags-display label'))
        .map(label => label.outerHTML.trim());
   
    
    // 기존 리뷰 데이터 가져오기
    const name = reviewElement.querySelector('.reviewer-name').textContent;
    const rating = reviewElement.querySelector('.review-rating').dataset.rating;
    const content = reviewElement.querySelector('.review-content').textContent;
    
    // 이미지 데이터 가져오기
    const existingImages = Array.from(reviewElement.querySelectorAll('.review-image')).map(img => img.src);
    selectedImages = [...existingImages];

    const existingTags = Array.from(reviewElement.querySelectorAll('.review-tags-display label'))
    .map(label => label.outerHTML.trim());

    console.log("기존 태그(HTML):", existingTags);
   

    // 모달 폼에 데이터 설정
    document.getElementById('name').value = name;
    document.getElementById('content').value = content;
    updateStars(parseFloat(rating));

    // 기존 태그 체크하기
    document.querySelectorAll('.tag-checkbox').forEach(checkbox => {
        const label = document.querySelector(`label[for="${checkbox.id}"]`);
        const labelHTML = label?.outerHTML.trim(); // ✅ label 전체 HTML 비교

        checkbox.checked = existingTags.includes(labelHTML);
    });
    
    
        // 이미지 미리보기 설정
    imagePreviewContainer.innerHTML = '';
    existingImages.forEach(imageUrl => {
        addImagePreview(imageUrl);
    });

    // 모달 제목 변경
    document.querySelector('.modal-content h2').textContent = '리뷰 수정';
    document.querySelector('.submit-review').textContent = '수정완료';

    // 모달 열기
    console.log("✅ openReviewModal() 실행 전!"); // ✅ 실행 전 로그 추가
   
    openReviewModal();

    
}

function updateTagCountsOnEdit(prevTags, newTags) {
    console.log(prevTags);
    const tagElements = document.querySelectorAll('.tag');

    // ✅ 기존 태그 리스트 (텍스트만 추출)
    const prevTagTexts = prevTags.map(prevTag => {
        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = prevTag;
        console.log(prevTag)
        return tempDiv.querySelector('.tag-text')?.textContent.trim();
    }).filter(Boolean); // null 값 제거

    // ✅ 새로운 태그 리스트 (텍스트만 추출)
    const newTagTexts = newTags.map(newTag => {
        const tempDiv = document.createElement('div');
        
        tempDiv.innerHTML = newTag;
        return tempDiv.querySelector('.tag-text')?.textContent.trim();
    }).filter(Boolean); // null 값 제거

    console.log("🔽 기존 태그:", prevTagTexts);
    console.log("🔼 새로운 태그:", newTagTexts);

    // ✅ 삭제된 태그 → 카운트 감소
    prevTagTexts.forEach(prevTagText => {
        if (!newTagTexts.includes(prevTagText)) {
            console.log(` 삭제된 태그: ${prevTagText}`);

            const tagElement = Array.from(tagElements).find(tag =>
                tag.querySelector('.text').textContent.trim() === prevTagText
            );

            if (tagElement) {
                let countElement = tagElement.querySelector('.count');
                let currentCount = parseInt(countElement.textContent.trim(), 10);
                if (currentCount > 1) {
                    countElement.textContent = currentCount - 1;
                } else {
                    tagElement.remove();
                }
            }
        }
    });
    // ✅ 추가된 태그 → 카운트 증가
    newTagTexts.forEach(newTagText => {
        if (!prevTagTexts.includes(newTagText)) {
            console.log(` 추가된 태그: ${newTagText}`);

            const tagElement = Array.from(tagElements).find(tag =>
                tag.querySelector('.text').textContent.trim() === newTagText
            );

            if (tagElement) {
                let countElement = tagElement.querySelector('.count');
                countElement.textContent = parseInt(countElement.textContent.trim(), 10) + 1;
            } else {
                // 태그가 없으면 새로 추가
                const tagContainer = document.querySelector('.tag-container');
                const newTagElement = document.createElement('div');
                newTagElement.classList.add('tag');
                newTagElement.innerHTML = `
                    <div class="tag-header">
                        <div class="tag-left">
                            <span class="text">${newTagText}</span>
                        </div>
                        <span class="count">1</span>
                    </div>
                    <div class="gauge-container">
                        <div class="gauge"></div>
                    </div>
                `;
                tagContainer.appendChild(newTagElement);
            }
        }
    });

    // ✅ 게이지 업데이트
    updateGauge();
}




});


    // ===== 이미지 슬라이더 기능 =====
    const slider = document.querySelector('.slider');
    const slides = document.querySelectorAll('.slide');
    const prevButton = document.querySelector('.prev');
    const nextButton = document.querySelector('.next');
    let currentSlide = 0;

    function updateSlider() {
        slider.style.transform = `translateX(-${currentSlide * 100}%)`;
    }

    prevButton.addEventListener('click', () => {
        currentSlide = (currentSlide - 1 + slides.length) % slides.length;
        updateSlider();
    });

    nextButton.addEventListener('click', () => {
        currentSlide = (currentSlide + 1) % slides.length;
        updateSlider();
    });

    // ===== 장바구니 기능 =====
    const addToCartButtons = document.querySelectorAll('.add-to-cart');
    const cartItems = document.querySelector('.cart-items');
    const cartTotal = document.querySelector('.cart-total span:last-child');
    const clearCartButton = document.querySelector('.clear-cart');
    let total = 0;

    // 장바구니 초기화
    if (cartItems.children.length === 0) {
        cartItems.innerHTML = '아직 담긴 상품이 없습니다.';
    }

    // 전체 비우기 버튼
    clearCartButton.addEventListener('click', () => {
        cartItems.innerHTML = '아직 담긴 상품이 없습니다.';
        total = 0;
        updateTotal();
    });

    // 장바구니 아이템 삭제
    function removeCartItem(itemDiv) {
        itemDiv.remove();
        if (cartItems.children.length === 0) {
            cartItems.innerHTML = '아직 담긴 상품이 없습니다.';
        }
        updateTotal();
    }

    // 총액 업데이트
    function updateTotal() {
        total = 0;
        const cartItemElements = document.querySelectorAll('.cart-item');
        cartItemElements.forEach(item => {
            const price = parseInt(item.querySelector('.cart-item-price').textContent.replace(/[^0-9]/g, ''));
            const quantity = parseInt(item.querySelector('.quantity').textContent);
            total += price * quantity;
        });
        cartTotal.textContent = `${total.toLocaleString()}원`;
    }

    // 장바구니 담기 기능
    addToCartButtons.forEach(button => {
        button.addEventListener('click', () => {
            const menuItem = button.closest('.menu-item');
            const name = menuItem.querySelector('.menu-name').textContent;
            const price = parseInt(menuItem.querySelector('.menu-price').textContent.replace(/[^0-9]/g, ''));
            
            // 장바구니에 같은 상품이 있는지 확인
            const existingItem = Array.from(cartItems.children).find(item => 
                item.querySelector('.cart-item-name')?.textContent === name
            );

            if (existingItem) {
                // 이미 있는 상품이면 수량만 증가
                const quantitySpan = existingItem.querySelector('.quantity');
                const quantity = parseInt(quantitySpan.textContent) + 1;
                quantitySpan.textContent = quantity;
                updateTotal();
            } else {
                // 새로운 상품이면 장바구니에 추가
                if (cartItems.textContent.trim() === '아직 담긴 상품이 없습니다.') {
                    cartItems.innerHTML = '';
                }
                
                const itemDiv = document.createElement('div');
                itemDiv.className = 'cart-item';
                itemDiv.innerHTML = `
                    <div class="cart-item-info">
                        <span class="cart-item-name">${name}</span>
                        <span class="cart-item-price">${price.toLocaleString()}원</span>
                    </div>
                    <div class="cart-item-controls">
                        <button class="quantity-btn minus">-</button>
                        <span class="quantity">1</span>
                        <button class="quantity-btn plus">+</button>
                    </div>
                `;
                cartItems.appendChild(itemDiv);

                // 수량 조절 버튼 이벤트
                const plusBtn = itemDiv.querySelector('.plus');
                const minusBtn = itemDiv.querySelector('.minus');
                const quantitySpan = itemDiv.querySelector('.quantity');

                plusBtn.addEventListener('click', () => {
                    const quantity = parseInt(quantitySpan.textContent) + 1;
                    quantitySpan.textContent = quantity;
                    updateTotal();
                });

                minusBtn.addEventListener('click', () => {
                    const quantity = parseInt(quantitySpan.textContent);
                    if (quantity > 1) {
                        quantitySpan.textContent = quantity - 1;
                        updateTotal();
                    } else {
                        removeCartItem(itemDiv);
                    }
                });

                updateTotal();
            }
        });
    });

    // ===== 영업시간 토글 기능 =====
    const hoursToggle = document.querySelector('.hours-toggle');
    const hoursDetail = document.querySelector('.hours-detail');
    let isExpanded = false;

    // 오늘의 영업시간 표시
    const days = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
    const today = new Date().getDay();
    const todayHours = document.getElementById('today-hours');
    const timeString = today === 0 || today === 6 ? '09:00 - 20:00' : '07:00 - 22:00';
    todayHours.textContent = `오늘 ${timeString}`;

    hoursToggle.addEventListener('click', () => {
        isExpanded = !isExpanded;
        hoursDetail.style.display = isExpanded ? 'block' : 'none';
        hoursToggle.textContent = isExpanded ? '접기' : '더보기';
    });

    


  

   

    // ===== 리뷰 정렬 기능 =====
    const reviewSort = document.getElementById('reviewSort');
    const reviewList = document.querySelector('.review-list');

    function sortReviews(sortBy) {
        const reviews = Array.from(document.querySelectorAll('.review'));
        
        reviews.sort((a, b) => {
            const ratingA = parseFloat(a.querySelector('.review-rating').dataset.rating);
            const ratingB = parseFloat(b.querySelector('.review-rating').dataset.rating);
            const dateA = new Date(a.querySelector('.review-date').textContent.split(' ')[0]);
            const dateB = new Date(b.querySelector('.review-date').textContent.split(' ')[0]);

            switch(sortBy) {
                case 'highest':
                    return ratingB - ratingA;
                case 'lowest':
                    return ratingA - ratingB;
                case 'latest':
                default:
                    return dateB - dateA;
            }
        });

        reviewList.innerHTML = '';
        reviews.forEach(review => reviewList.appendChild(review));
    }

    // 리뷰 정렬 이벤트
    reviewSort?.addEventListener('change', (e) => {
        sortReviews(e.target.value);
    });


    
    
    
   
// 이미지 클릭 시 큰 화면으로 보기
document.querySelector('.reviews').addEventListener('click', (e) => {
    if (e.target.classList.contains('review-image')) {
        const modal = document.createElement('div');
        modal.className = 'image-modal';
        modal.innerHTML = `
            <div class="modal-content">
                <img src="${e.target.src}" alt="확대된 이미지">
            </div>
        `;
        document.body.appendChild(modal);

        modal.addEventListener('click', () => {
            modal.remove();
        });
    }
});
// 날짜 포맷팅 함수 추가
function formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    
    return `${year}-${month}-${day} ${hours}:${minutes}`;
}







// 전역 변수로 현재 수정 중인 리뷰 요소를 저장
let currentEditingReview = null;

// 리뷰 수정 시작 함수
function closeReviewModal() {

    const modal = document.getElementById('reviewModal');
    if (!modal) {
        console.error("❌ 'reviewModal' 요소를 찾을 수 없습니다!");
        return;
    }

    modal.style.display = 'none';
  
    document.body.style.overflow = 'auto';
    
}



   

const carouselContainer = document.querySelector('.carousel'); // 캐러셀 전체 컨테이너
const carouselItems = document.querySelectorAll('.carousel-slide'); // 개별 슬라이드
const prevCarouselButton = document.querySelector('.carousel-prev'); // 이전 버튼
const nextCarouselButton = document.querySelector('.carousel-next'); // 다음 버튼
const dotsWrapper = document.querySelector('.carousel-dots'); // 도트 컨테이너
let activeIndex = 0; // 현재 활성화된 슬라이드 인덱스

function updateCarouselPosition() {
    // 슬라이드 이동 (백틱 사용하여 template literal 적용)
    carouselContainer.style.transform = `translateX(-${activeIndex * 100}%)`;

    // 활성화된 도트 표시
    document.querySelectorAll('.carousel-dot').forEach((dot, index) => {
        dot.classList.toggle('active', index === activeIndex);
    });
}

function generateDots() {
    carouselItems.forEach((_, index) => {
        const dotElement = document.createElement('span');
        dotElement.classList.add('carousel-dot');
        dotElement.addEventListener('click', () => {
            activeIndex = index;
            updateCarouselPosition();
        });
        dotsWrapper.appendChild(dotElement);
    });
    updateCarouselPosition();
}

// ⛔ 이벤트 리스너에서 잘못된 변수명 수정
prevCarouselButton.addEventListener('click', () => {
    activeIndex = (activeIndex - 1 + carouselItems.length) % carouselItems.length;
    updateCarouselPosition();
});

nextCarouselButton.addEventListener('click', () => {
    activeIndex = (activeIndex + 1) % carouselItems.length;
    updateCarouselPosition();
});

generateDots();





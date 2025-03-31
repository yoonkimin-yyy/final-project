


document.addEventListener("DOMContentLoaded", () => {
	
	
    
    const popupInfo = document.getElementById("popup-info");
    const closePopup = document.querySelector(".close");

    const filterButtons = document.querySelectorAll(".filter-btn");
    const datePicker = document.getElementById("date-picker");

    const infoDivFirst = document.getElementById("info-div-first");
    const infoDivSecond = document.getElementById("info-div-second");
    const infoDivThird = document.getElementById("info-div-third");
    const infoDivFourth = document.getElementById("info-div-fourth");
    const infoDivFifth = document.getElementById("info-div-fifth");
	
	// 상태 버튼  가져오기
	const approveBtn = document.getElementById("approve-btn");
	const rejectBtn = document.getElementById("reject-btn");
	const completeBtn = document.getElementById("complete-btn");
	const rejectReasonInput = document.getElementById("rejection-reason");
	// 여기까지
	
	const orderList = document.getElementById("order-list"); // ✅ 이벤트 위임 대상 변경

    function getTodayDate() {
        return new Date().toISOString().split("T")[0];
    }
	
	
	
    function applyRowColors() {
        document.querySelectorAll("#order-list tr").forEach(row => {
            const status = row.getAttribute("data-status");
            row.classList.remove("pending", "approved", "rejected", "completed");

            if (status === "대기") row.classList.add("pending");
            else if (status === "승인") row.classList.add("approved");
            else if (status === "거절") row.classList.add("rejected");
            else if (status === "완료") row.classList.add("completed");
        });
    }

    function approveOrder(orderNo) {
		const popup = document.getElementById("popup-" + orderNo);
		
		if (popup) {
		    popup.style.display = "none";  // 팝업 숨기기
		}
        updateOrderCounts();
        applyRowColors();
		updateOrderStatus(orderNo, "승인", "");


		
    }
	
	orderList.addEventListener("click", (event) => {
	        const row = event.target.closest("tr"); // tr 찾기
			if (row) {
			        const orderNo = row.querySelector("td:first-child").textContent.trim();  // 주문 번호 추출
			        showOrderPopup(orderNo);  // 주문 번호를 전달하여 팝업을 열기
			}
	});

    function rejectOrder(orderNo) {
		const popup = document.getElementById("popup-" + orderNo);
				
        const reasonInput = document.getElementById("rejection-reason-"+orderNo);
        const reason = reasonInput ? reasonInput.value.trim() : "";
		
		if(!reason || reason.trim() === ""){
			alert("거절 사유를 입력해주세요.");
			return;
		}
		
		if (popup) {
			popup.style.display = "none";  // 팝업 숨기기
		}
        
        updateOrderCounts();
        applyRowColors();
		updateOrderStatus(orderNo, "거절", reason);
        
        

        
        
    }

    function completeOrder(orderNo) {
		const popup = document.getElementById("popup-" + orderNo);
		if (popup) {
			popup.style.display = "none";  // 팝업 숨기기
		}
		const completeBtn = document.getElementById("complete-btn-"+orderNo);
        updateOrderCounts();
        applyRowColors();
		updateOrderStatus(orderNo, "완료", "");
						
		
    }

    function updateOrderCounts() {
        let pending = 0, approved = 0, rejected = 0, completed = 0;
        let totalSales = 0;

        document.querySelectorAll("#order-list tr").forEach(row => {
			
			
			
            if (row.style.display === "none") return;

            const status = row.getAttribute("data-status");
            const priceText = row.cells[3]?.textContent || "0원";
            const price = parseInt(priceText.replace(/[^0-9]/g, ""), 10) || 0;

            if (status === "대기") pending++;
            else if (status === "승인") approved++;
            else if (status === "거절") rejected++;
            else if (status === "완료") {
                completed++;
                totalSales += price;
            }
        });

        infoDivFirst.textContent = `대기 : ${pending}개`;
        infoDivSecond.textContent = `승인 : ${approved}개`;
        infoDivThird.textContent = `거절 : ${rejected}개`;
        infoDivFourth.textContent = `완료 : ${completed}개`;
        infoDivFifth.textContent = `총 매출 : ${totalSales.toLocaleString()}원`;
    }

	function filterOrders(status) {
	    const selectedDate = datePicker.value; // 선택된 날짜
	    const selectedDateObj = new Date(selectedDate); // 선택된 날짜를 Date 객체로 변환

	    document.querySelectorAll("#order-list tr").forEach(row => {
	        const orderStatus = row.getAttribute("data-status");
	        const orderDate = row.getAttribute("data-date")?.split(" ")[0]; // 날짜만 추출

	        // 주문 날짜가 "YYYY-MM-DD" 형식으로 정확한지 확인하고, 변환
			
			// 주문 날짜가 유효한 경우 연도, 월, 일만 비교
			
			
			const selectedDateObj = new Date(selectedDate);
			const orderDateObj = new Date(orderDate);

			
			
			

	        // 주문 날짜가 유효하고, 선택된 날짜와 비교
			row.style.display = (orderDateObj.getMonth() === selectedDateObj.getMonth() && orderDateObj.getDate() === selectedDateObj.getDate()) &&
			                    (status === "전체" || orderStatus === status) ? "table-row" : "none";
	    });

	    updateOrderCounts();
	    applyRowColors();
	}
	function isValidDate(dateString) {
	    const date = new Date(dateString);
	    return !isNaN(date.getTime());
	}
	
	setInterval(function() {
	    $.ajax({
	        url: location.href,  // 현재 페이지 URL을 통해 데이터를 가져옴
	        type: "GET",
	        success: function(response) {
	            // 서버에서 새로운 데이터를 가져온 후, #order-list를 갱신하는 부분
	            var newContent = $(response).find("#order-list").html(); // 새로 가져온 HTML의 #order-list 부분만 가져옴
	            
	            // 기존 #order-list의 데이터와 새 데이터를 비교해서, 테이블을 갱신
	            $("#order-list").html(newContent);
				
				
	            // 필터링을 새로 적용하도록
	            const activeStatus = document.querySelector(".filter-btn.active")?.getAttribute("data-filter") || "전체";
	            filterOrders(activeStatus);  // 필터링을 다시 실행
				applyRowColors();  // 색상도 다시 적용
				
				
				// 밑에 완료 버튼 없애는 로직 추가
				document.querySelectorAll("tr[data-order-no]").forEach(row => {
				        const orderNo = row.getAttribute("data-order-no");  // 주문 번호
				        const status = row.getAttribute("data-status");  // 상태 값 가져오기


				        // 주문 번호를 기반으로 팝업을 찾음
				        const popup = document.querySelector(`#popup-${orderNo}`);
				        
				        // 팝업이 있으면 그 안에서 버튼을 찾음
				        if (popup) {
				            const approveBtn = popup.querySelector(`#approve-btn-${orderNo}`);
				            const rejectBtn = popup.querySelector(`#reject-btn-${orderNo}`);
				            const completeBtn = popup.querySelector(`#complete-btn-${orderNo}`);
							const rejectDetail = popup.querySelector(`#rejection-reason-${orderNo}`);


				            // 상태에 따라 버튼 보이기/숨기기
				            if (approveBtn && rejectBtn && completeBtn) {
				                if (status === "대기") {
				                    approveBtn.style.display = "inline-block";
				                    rejectBtn.style.display = "inline-block";
				                    completeBtn.style.display = "none";
									rejectDetail.style.display = "inline-block";
				                } else if (status === "승인") {
				                    approveBtn.style.display = "none";
				                    rejectBtn.style.display = "none";
				                    completeBtn.style.display = "inline-block";
									rejectDetail.style.display = "none";
				                } else if (status === "거절" || status === "완료") {
				                    approveBtn.style.display = "none";
				                    rejectBtn.style.display = "none";
				                    completeBtn.style.display = "none";
									rejectDetail.style.display = "none";
				                }
				            }
				        }
				    });			
				
	        }
	    });
	}, 2000);
	
	
	
	
	
	
	// 여기부터 수정해야 하는 부분
	function showOrderPopup(orderNo, isReject = false) {
		
		const popup = document.getElementById("popup-"+orderNo);
		
		
		
	    

	    
		
		if (popup) {
		        const row = document.querySelector(`tr[data-order-no='${orderNo}']`); // 이 부분은 `row`를 직접 찾아서 데이터를 추출해도 됩니다.
				const customerPhone = row.cells[4].textContent; // 고객 전화번호
				const orderTime = row.cells[2].textContent; // 주문 시간
				const totalPrice = row.cells[3].textContent || "0원";  // 주문 금액

				document.getElementById("popup-customer-phone").textContent = customerPhone;
				document.getElementById("popup-order-time").textContent = orderTime;
				document.getElementById("popup-total-price").textContent = totalPrice;

		        // 팝업 표시
		        popup.style.display = "block";  // 동적으로 찾은 팝업 표시
		}


	    

		
		

	    const approveBtn = document.getElementById("approve-btn-"+orderNo);
	    const rejectBtn = document.getElementById("reject-btn-"+orderNo);
		const completeBtn = document.getElementById("complete-btn-"+orderNo);

	    // 이벤트 리스너 추가하기 전에 버튼이 존재하는지 확인
	    if (approveBtn) {
	        approveBtn.addEventListener("click", () => {
	            approveOrder(orderNo); // 승인 처리
				
	        });
		}
		if(rejectBtn){
			
	        rejectBtn.addEventListener("click", () => {
	            
	            rejectOrder(orderNo); // 거절 처리
	            
	        });
		}
			
		if(completeBtn){	
			completeBtn.addEventListener("click", () => {
				    completeOrder(orderNo);
			
			});
	    }

	    
		
		// 팝업 닫기 버튼 클릭 시 팝업을 닫기 위한 이벤트 리스너 추가
		        popup.querySelector(".close").addEventListener("click", () => {
		            popup.style.display = "none";  // 팝업 닫기
		        });
	} // 여기까지
	
	


    datePicker.addEventListener("change", () => {
		const activeStatus = document.querySelector(".filter-btn.active")?.getAttribute("data-filter") || "전체";
        filterOrders(activeStatus);
    });
	
	

    filterButtons.forEach(button => {
        button.addEventListener("click", () => {
            filterButtons.forEach(btn => btn.classList.remove("active"));
            button.classList.add("active");

            filterOrders(button.getAttribute("data-filter"));
        });
    });

    


    datePicker.value = getTodayDate();
    filterOrders("전체");
    applyRowColors();
	
	// 버튼에 따른 상태 변경 코드
	// 상태 업데이트 함수
	function updateOrderStatus(orderNo, status, rejectionReason) {
	    const statusUpdateDTO = {
			payDTO:{
				orderNo: orderNo
			},
			statusDTO:{
				pickupStatus: status,
				rejectionReason: rejectionReason || ""
			}
			
		
	        
	    };

	    fetch(`/pickup/update-status`, {
	        method: "POST",
	        headers: {
	            "Content-Type": "application/json"
	        },
	        body: JSON.stringify(statusUpdateDTO)
	    })
	    .then(response => response.json())
	    .then(data => {
			
		})
		.catch(error => {
			   // 에러 처리
		});
		
	}

	document.addEventListener("click", (event) => {
	    const target = event.target;
	    const orderNo = target.dataset.orderNo;

	    if (!orderNo) return; // orderNo가 없으면 실행하지 않음

	    const orderNumberElement = document.getElementById(`popup-order-number-${orderNo}`);
	    if (!orderNumberElement) {
	        alert("주문 번호가 없습니다.");
	        return;
	    }

	    const orderNumber = orderNumberElement.textContent.trim();

	    // 승인 버튼 클릭 시
	    if (target.classList.contains("approve-btn")) {
	        updateOrderStatus(orderNumber, "승인", "");
	    }

	    // 거절 버튼 클릭 시
	    if (target.classList.contains("reject-btn")) {
	        const rejectionReasonElement = document.querySelector(`textarea[data-order-no='${orderNo}']`);
	        const rejectionReason = rejectionReasonElement ? rejectionReasonElement.value.trim() : "";

	        

	        updateOrderStatus(orderNumber, "거절", rejectionReason);
	    }

	    // 완료 버튼 클릭 시
	    if (target.classList.contains("complete-btn")) {
	        updateOrderStatus(orderNumber, "완료", "");
	    }
	});


	
	
	
	

    document.getElementById("sales-a").addEventListener("click", () => {
        window.open("연도별매출액.html", "연간매출", "width=600,height=900");
    });
	
	
	
});











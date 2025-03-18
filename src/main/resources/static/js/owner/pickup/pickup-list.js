


document.addEventListener("DOMContentLoaded", () => {
	
	
    const popup = document.getElementById("popup");
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

    function approveOrder(row) {
        updateOrderCounts();
        applyRowColors();
        popup.style.display = "none";

        row.setAttribute("data-status", "승인");

        updateOrderCounts();
        applyRowColors();

        // '승인' 버튼과 '거절' 버튼 숨기기
        const approveBtn = document.getElementById("approve-btn");
        const rejectBtn = document.getElementById("reject-btn");
        
            approveBtn.style.display = "none";
            rejectBtn.style.display = "none";
        

        // 거절 사유 입력 텍스트 박스 숨기기
        const rejectReasonInput = document.getElementById("rejection-reason");
            rejectReasonInput.style.display = "none";
        

		// '완료' 버튼이 이미 있는지 확인
		if (!document.querySelector(".complete-btn")) {
			   const completeBtn = document.createElement("button");
			   completeBtn.textContent = "완료";
			   completeBtn.classList.add("complete-btn");
			   completeBtn.id = "complete-btn";
			   popup.appendChild(completeBtn);

			   completeBtn.addEventListener("click", () => completeOrder(row));
		}
    }
	
	orderList.addEventListener("click", (event) => {
	        const row = event.target.closest("tr"); // tr 찾기
	        if (row) {
	            showOrderPopup(row);
	        }
	    });

    function rejectOrder(row) {
        const reasonInput = document.getElementById("rejection-reason");
        const reason = reasonInput ? reasonInput.value.trim() : "";
        if (!reason) {
            alert("거절 사유를 입력하세요.");
            return;
        }

        row.setAttribute("data-status", "거절");
        row.setAttribute("data-reason", reason);
        updateOrderCounts();
        applyRowColors();
        popup.style.display = "none";

        // '승인' 버튼과 '거절' 버튼 숨기기
        const approveBtn = document.getElementById("approve-btn");
        const rejectBtn = document.getElementById("reject-btn");
    
            approveBtn.style.display = "none";
            rejectBtn.style.display = "none";
        

        // 거절 사유 입력 텍스트 박스 숨기기
        const rejectReasonInput = document.getElementById("rejection-reason");
        
            rejectReasonInput.style.display = "none";
        
    }

    function completeOrder(row) {
		const completeBtn = document.getElementById("complete-btn");
        row.setAttribute("data-status", "완료");
        updateOrderCounts();
        applyRowColors();
        popup.style.display = "none";
		completeBtn.style.display = "none";
		
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
			                    (status === "전체" || orderStatus === status) ? "teble-row" : "none";
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
	        }
	    });
	}, 5000);
	
	
	
	
	
	
	// 여기부터 수정해야 하는 부분
	function showOrderPopup(row, isReject = false) {
	    const orderNo = row.cells[0].textContent; // 주문 번호
	    const customerPhone = row.cells[4].textContent; // 고객 전화번호
	    const orderTime = row.cells[2].textContent; // 주문 시간
	    const totalPrice = row.cells[3].textContent || "0원"; // 주문 금액
	    const menuData = row.getAttribute("data-menu") || ""; // 메뉴 목록

	    // 팝업에 데이터 삽입
	    document.getElementById("popup-order-number").textContent = orderNo;
	    document.getElementById("popup-customer-phone").textContent = customerPhone;
	    document.getElementById("popup-order-time").textContent = orderTime;
	    document.getElementById("popup-total-price").textContent = totalPrice;
		
		


	    // 메뉴 목록 표시
	    const menuList = document.getElementById("popup-menu-list");
	    menuList.innerHTML = ""; // 기존 메뉴 목록 지우기
	    const menuItems = menuData.split(",").map(item => {
	        const parts = item.trim().split(" ");
	        if (parts.length >= 3) {
	            return `<li>${parts[0]} - ${parts[1]}개 - ${parts[2]}원</li>`;
	        }
	        return `<li>${item}</li>`;
	    }).join("");
	    menuList.innerHTML = menuItems;

		
		

	    const approveBtn = document.getElementById("approve-btn");
	    const rejectBtn = document.getElementById("reject-btn");

	    // 이벤트 리스너 추가하기 전에 버튼이 존재하는지 확인
	    if (approveBtn && rejectBtn) {
	        approveBtn.addEventListener("click", () => {
	            approveOrder(row); // 승인 처리
	        });

	        rejectBtn.addEventListener("click", () => {
	            const reason = document.getElementById("rejection-reason").value.trim();
	            if (!reason) {
	                alert("거절 사유를 입력하세요.");
	            } else {
	                rejectOrder(row); // 거절 처리
	            }
	        });
	    }

	    // 팝업을 표시
	    const popup = document.getElementById("popup");
	    popup.style.display = "block";
		
		// 팝업 닫기 버튼이 있을 경우 이벤트 리스너 추가
		const closePopup = document.querySelector(".close");
		closePopup.addEventListener("click", () => {
		    popup.style.display = "none"; // 팝업 닫기
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
		const cleanOrderNo = orderNo.toString().trim();
	    const statusUpdateDTO = {
			payDTO:{
				orderNo: Number(cleanOrderNo)
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
	        if (data.success) {
	            alert(`${status} 상태로 업데이트되었습니다.`);
	            // UI 업데이트 추가
	        } else {
	            alert(status);
	        }
	    })
	    .catch(error => {
			
	    });
		
	}

	// 승인 버튼 클릭 시
	document.addEventListener("click", (event) => {
	    if (event.target && event.target.id === "approve-btn") {
	        const orderNo = document.getElementById("popup-order-number");
	        if (!orderNo) {
	            alert("주문 번호가 없습니다.");
	            return;
	        }
	        updateOrderStatus(orderNo.textContent.trim(), "승인", "");
		}
	    
	});;

	// 거절 버튼 클릭 시
	rejectBtn.addEventListener("click", (event) => {
		if (event.target && event.target.id === "reject-btn") {
			const orderNo = document.getElementById("popup-order-number");
			const rejectionReason = document.getElementById("rejection-reason").value.trim();
			if (!orderNo) {
				alert("주문 번호가 없습니다.");
				return;
			} else if (!rejectionReason) {
		        alert("거절 사유를 입력하세요.");
		        return;
		    }
	    	updateOrderStatus(orderNo.textContent.trim(), "거절", rejectionReason);
		}
	});

	// 완료 버튼 클릭 시
	document.addEventListener("click", (event) => {
		if (event.target && event.target.id === "complete-btn") {
			const orderNo = document.getElementById("popup-order-number");
			if (!orderNo) {
				alert("주문 번호가 없습니다.");
				return;
			} 
			updateOrderStatus(orderNo.textContent.trim(), "완료", "");
		}
		
		
		
	});


	closePopup.addEventListener("click", () => {
	    popup.style.display = "none";
	});
	
	
	document.addEventListener("click", function (event) {
	    const row = event.target.closest("#order-list tr"); 
	    if (row) {
	        showOrderPopup(row);
	    }
	});

    document.getElementById("yearly-report-btn").addEventListener("click", () => {
        window.open("연도별매출액.html", "연간매출", "width=600,height=900");
    });
});








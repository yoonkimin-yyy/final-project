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
	
	const approveBtn = document.getElementById("approve-btn");
	const rejectBtn = document.getElementById("reject-btn");
	const completeBtn = document.getElementById("complete-btn");
	const rejectReasonInput = document.getElementById("rejection-reason");
	
	const orderList = document.getElementById("order-list");

    function getTodayDate() {
        return new Date().toISOString().split("T")[0];
    }
	
    function applyRowColors() {
        document.querySelectorAll("#order-list tr").forEach(row => {
            const status = row.getAttribute("data-status");
            row.classList.remove("pending", "approved", "rejected", "completed");

            if (status === "대기중") row.classList.add("pending");
            else if (status === "승인") row.classList.add("approved");
            else if (status === "거절") row.classList.add("rejected");
            else if (status === "완료") row.classList.add("completed");
        });
    }

    function approveOrder(orderNo) {
		const popup = document.getElementById("popup-" + orderNo);
		
		if (popup) {
		    popup.style.display = "none";  
		}
        updateOrderCounts();
        applyRowColors();
		updateOrderStatus(orderNo, "승인", "");
    }
	
	orderList.addEventListener("click", (event) => {
	        const row = event.target.closest("tr"); 
			if (row) {
			        const orderNo = row.querySelector("td:first-child").textContent.trim(); 
			        showOrderPopup(orderNo);  
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
			popup.style.display = "none";  
		}
        
        updateOrderCounts();
        applyRowColors();
		updateOrderStatus(orderNo, "거절", reason);
    }

    function completeOrder(orderNo) {
		const popup = document.getElementById("popup-" + orderNo);
		if (popup) {
			popup.style.display = "none";  
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

            if (status === "대기중") pending++;
            else if (status === "승인") approved++;
            else if (status === "거절") rejected++;
            else if (status === "완료") {
                completed++;
                totalSales += price;
            }
        });

        infoDivFirst.textContent = `대기중 : ${pending}개`;
        infoDivSecond.textContent = `승인 : ${approved}개`;
        infoDivThird.textContent = `거절 : ${rejected}개`;
        infoDivFourth.textContent = `완료 : ${completed}개`;
        infoDivFifth.textContent = `총 매출(완료) : ${totalSales.toLocaleString()}원`;
    }

	function filterOrders(status) {
	    const selectedDate = datePicker.value; 
	    const selectedDateObj = new Date(selectedDate); 

	    document.querySelectorAll("#order-list tr").forEach(row => {
	        const orderStatus = row.getAttribute("data-status");
	        const orderDate = row.getAttribute("data-date")?.split(" ")[0]; // 날짜만 추출
			
			const selectedDateObj = new Date(selectedDate);
			const orderDateObj = new Date(orderDate);
	        
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
	
	document.querySelectorAll("tr[data-order-no]").forEach(row => {
			const orderNo = row.getAttribute("data-order-no"); 
			const status = row.getAttribute("data-status"); 
			const popup = document.querySelector(`#popup-${orderNo}`);
					        
			if (popup) {
					  const approveBtn = popup.querySelector(`#approve-btn-${orderNo}`);
					  const rejectBtn = popup.querySelector(`#reject-btn-${orderNo}`);
					  const completeBtn = popup.querySelector(`#complete-btn-${orderNo}`);
					  const rejectDetail = popup.querySelector(`#rejection-reason-${orderNo}`);
					          
					  if (approveBtn && rejectBtn && completeBtn) {
					      if (status === "대기중") {
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
	
	function showOrderPopup(orderNo) {
		const popup = document.getElementById("popup-"+orderNo);
			
			if (popup) {
			        const row = document.querySelector(`tr[data-order-no='${orderNo}']`); // 이 부분은 `row`를 직접 찾아서 데이터를 추출해도 됩니다.
					const customerPhone = row.cells[4].textContent; // 고객 전화번호
					const orderTime = row.cells[2].textContent; // 주문 시간
					const totalPrice = row.cells[3].textContent || "0원";  // 주문 금액

					document.getElementById("popup-customer-phone").textContent = customerPhone;
					document.getElementById("popup-order-time").textContent = orderTime;
					document.getElementById("popup-total-price").textContent = totalPrice;

			        popup.style.display = "block";  
			}
		    const approveBtn = document.getElementById("approve-btn-"+orderNo);
		    const rejectBtn = document.getElementById("reject-btn-"+orderNo);
			const completeBtn = document.getElementById("complete-btn-"+orderNo);

		    if (approveBtn) {
		        approveBtn.addEventListener("click", () => {
		            approveOrder(orderNo); 
					
		        });
			}
			if(rejectBtn){
		        rejectBtn.addEventListener("click", () => {
		            rejectOrder(orderNo); 
		        });
			}
				
			if(completeBtn){	
				completeBtn.addEventListener("click", () => {
					completeOrder(orderNo);
				});
		    }
			
			popup.querySelector(".close").addEventListener("click", () => {
				  popup.style.display = "none"; 
			});
	}
	
	setInterval(function() {
	    $.ajax({
	        url: location.href,  
	        type: "GET",
	        success: function(response) {
	            
	            var newContent = $(response).find("#order-list").html();  
	            $("#order-list").html(newContent);  

	            document.getElementById("order-list").addEventListener("click", function(event) {
	                const row = event.target.closest("tr[data-order-no]"); 
	                if (row) {
	                    const orderNo = row.getAttribute("data-order-no");
	                    showOrderPopup(orderNo);
	                }
	            });

	            document.querySelectorAll(".popup").forEach(popup => {
	                const closeButton = popup.querySelector(".close");
	                if (closeButton) {
	                    closeButton.addEventListener("click", () => {
	                        popup.style.display = "none";
	                    });
	                }
	            });

	            const activeStatus = document.querySelector(".filter-btn.active")?.getAttribute("data-filter") || "전체";
	            filterOrders(activeStatus);  
	            applyRowColors(); 

	            document.querySelectorAll("tr[data-order-no]").forEach(row => {
	                const orderNo = row.getAttribute("data-order-no"); 
	                const status = row.getAttribute("data-status"); 
	                const popup = document.getElementById("popup-"+orderNo);
	                
	                if (popup) {
	                    const approveBtn = popup.querySelector(`#approve-btn-${orderNo}`);
	                    const rejectBtn = popup.querySelector(`#reject-btn-${orderNo}`);
	                    const completeBtn = popup.querySelector(`#complete-btn-${orderNo}`);
	                    const rejectDetail = popup.querySelector(`#rejection-reason-${orderNo}`);

	                    if (approveBtn && rejectBtn && completeBtn) {
	                        if (status === "대기중") {
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
			  
		});
	}

	document.addEventListener("click", (event) => {
	    const target = event.target;
	    const orderNo = target.dataset.orderNo;

	    if (!orderNo) return; 

	    const orderNumberElement = document.getElementById(`popup-order-number-${orderNo}`);
	    if (!orderNumberElement) {
	        alert("주문 번호가 없습니다.");
	        return;
	    }

	    const orderNumber = orderNumberElement.textContent.trim();
	    
	    if (target.classList.contains("approve-btn")) {
	        updateOrderStatus(orderNumber, "승인", "");
	    }
	    if (target.classList.contains("reject-btn")) {
	        const rejectionReasonElement = document.querySelector(`textarea[data-order-no='${orderNo}']`);
	        const rejectionReason = rejectionReasonElement ? rejectionReasonElement.value.trim() : "";
	        updateOrderStatus(orderNumber, "거절", rejectionReason);
	    }
	    if (target.classList.contains("complete-btn")) {
	        updateOrderStatus(orderNumber, "완료", "");
	    }
	});

    document.getElementById("sales-a").addEventListener("click", () => {
        window.open("연도별매출액.html", "연간매출", "width=600,height=900");
    });
});
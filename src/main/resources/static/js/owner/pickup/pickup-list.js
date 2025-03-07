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
        

        // '완료' 버튼 추가
        const completeBtn = document.createElement("button");
        completeBtn.textContent = "완료";
        completeBtn.classList.add("complete-btn");
        popup.appendChild(completeBtn);

        completeBtn.addEventListener("click", () => completeOrder(row));
    }

    function rejectOrder(row) {
        const reasonInput = document.getElementById("rejection-reason");
        const reason = reasonInput ? reasonInput.value.trim() : "";
        console.log(reasonInput);
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
        row.setAttribute("data-status", "완료");
        updateOrderCounts();
        applyRowColors();
        popup.style.display = "none";
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
        const selectedDate = datePicker.value;

        document.querySelectorAll("#order-list tr").forEach(row => {
            const orderStatus = row.getAttribute("data-status");
            const orderDate = row.getAttribute("data-date")?.split(" ")[0];

            row.style.display = (selectedDate === "" || orderDate === selectedDate) &&
                (status === "전체" || orderStatus === status) ? "" : "none";
        });

        updateOrderCounts();
        applyRowColors();
    }

    function showOrderPopup(row, isReject = false) {
        const orderNumber = row.cells[0].textContent;
        const customerPhone = row.cells[1].textContent;
        const orderTime = row.cells[2].textContent;
        const totalPrice = row.getAttribute("data-total-price") || "0원";
        const menuData = row.getAttribute("data-menu") || "";

        const menuListHTML = menuData.split(",").map(item => {
            const parts = item.trim().split(" ");
            if (parts.length >= 3) {
                const menuName = parts[0];
                const quantity = parts[1];
                const price = parts[2];
                return `<li>${menuName} - ${quantity} - ${price}</li>`;
            }
            return `<li>${item.trim()}</li>`;
        }).join("");

        

        const approveBtn = document.getElementById("approve-btn");
        const rejectBtn = document.getElementById("reject-btn");

        // 이벤트 리스너 추가하기 전에 버튼이 존재하는지 확인
        if (approveBtn && rejectBtn) {
            approveBtn.addEventListener("click", () => {
                approveOrder(row);
            });

            rejectBtn.addEventListener("click", () => {
                const reason = document.getElementById("rejection-reason").value.trim();
                console.log(reason);
                if (!reason) {
                    alert("거절 사유를 입력하세요.");
                } else {
                    rejectOrder(row);
                }
            });
        }

        popup.style.display = "block";
    }

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

    document.querySelectorAll("#order-list tr").forEach(row => {
        const detailButton = document.createElement("button");
        detailButton.textContent = "상세보기";
        detailButton.classList.add("detail-btn");
        row.cells[row.cells.length - 1].appendChild(detailButton);

        row.addEventListener("click", (e) => {
            if (!e.target.classList.contains("detail-btn")) {
                showOrderPopup(row);
            }
        });

        detailButton.addEventListener("click", (e) => {
            e.stopPropagation();
            showOrderPopup(row);
        });

        const rejectBtn = row.querySelector(".reject-btn");
        if (rejectBtn) {
            rejectBtn.addEventListener("click", (e) => {
                e.stopPropagation();
                showOrderPopup(row, true);
            });
        }
    });

    closePopup.addEventListener("click", () => {
        popup.style.display = "none";
    });

    datePicker.value = getTodayDate();
    filterOrders("전체");
    applyRowColors();

    document.getElementById("yearly-report-btn").addEventListener("click", () => {
        window.open("연도별매출액.html", "연간매출", "width=600,height=900");
    });
});

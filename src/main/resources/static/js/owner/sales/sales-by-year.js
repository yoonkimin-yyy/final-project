document.addEventListener("DOMContentLoaded", () => {
    const yearSelect = document.getElementById("year-select");
    const salesDataTable = document.getElementById("sales-data");

    // 현재 연도 구하기
    function getCurrentYear() {
        return new Date().getFullYear();
    }

    // 연도 선택 옵션 추가
    function populateYearOptions() {
        const currentYear = getCurrentYear();
        for (let i = currentYear; i >= currentYear - 5; i--) {
            const option = document.createElement("option");
            option.value = i;
            option.textContent = i;
            yearSelect.appendChild(option);
        }
    }

    // 연도 선택 시 매출 데이터 불러오기
    

    // 초기 실행
    populateYearOptions();
    loadSalesData(getCurrentYear());

    // 연도 변경 이벤트
    yearSelect.addEventListener("change", (event) => {
        loadSalesData(event.target.value);
    });
});

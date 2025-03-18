document.addEventListener("DOMContentLoaded", () => {
    const yearSelect = document.getElementById("year-select");
    const salesDataTable = document.getElementById("sales-data");

    // 현재 연도 구하기
    function getCurrentYear() {
        return new Date().getFullYear();
    }

    // 연도 선택 옵션 추가
    function populateYearOptions(availableYears, currentYear) {
        availableYears.forEach((year) => {
            const option = document.createElement("option");
            option.value = year;
            option.textContent = year;
            // 서버에서 받은 year와 일치하는 옵션 선택
            if (year === currentYear) {
                option.selected = true;
            }
            yearSelect.appendChild(option);
        });
    }

    // 서버에서 매출 데이터 불러오기
    function loadSalesData(year) {
        // 예시로 `fetch`를 사용해 AJAX 방식으로 데이터를 불러옵니다
        fetch(`/sales/annual?year=${year}`)
            .then(response => response.json())
            .then(data => {
                // 매출 데이터가 반환되면 해당 데이터를 테이블에 채워 넣음
                const monthlySalesMap = data.monthlySalesMap;
                const totalSales = data.totalSales;

                // 월별 매출액 채우기
                for (let month = 1; month <= 12; month++) {
                    const salesAmount = monthlySalesMap[month] || 0;
                    document.querySelector(`#month-${month}`).textContent = salesAmount;
                }

                // 총 매출액 채우기
                document.querySelector("#total-sales").textContent = totalSales;
            })
            .catch(error => {
				
            });
    }

    // 초기 실행: 연도 선택 드롭다운 채우기
    const availableYears = /* 서버에서 전달받은 사용 가능한 연도 목록 */;
    const currentYear = /* 서버에서 전달받은 선택된 연도 */;
    populateYearOptions(availableYears, currentYear);
    loadSalesData(currentYear);

    // 연도 변경 시 매출 데이터 불러오기
    yearSelect.addEventListener("change", (event) => {
        loadSalesData(event.target.value);
    });
});

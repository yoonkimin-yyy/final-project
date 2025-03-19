document.addEventListener("DOMContentLoaded", () => {
    const yearSelect = document.getElementById("year-select");
    
    // `data-*` 속성에서 값을 가져오고, JSON으로 변환
    const availableYears = JSON.parse(yearSelect.getAttribute("data-available-years"));
    const currentYear = parseInt(yearSelect.getAttribute("data-current-year"), 10);


    // 연도 선택 옵션 추가
    function populateYearOptions(availableYears, currentYear) {
        availableYears.forEach((year) => {
            const option = document.createElement("option");
            option.value = year;
            option.textContent = year;
            if (year === currentYear) {
                option.selected = true;
            }
            yearSelect.appendChild(option);
        });
    }

    // 초기 실행: 연도 선택 드롭다운 채우기
    populateYearOptions(availableYears, currentYear);

    // 연도 변경 시 폼 제출 (매출 데이터를 가져오고 페이지 새로고침)
    yearSelect.addEventListener("change", (event) => {
        const selectedYear = event.target.value;

        // 폼을 제출하여 서버로 선택된 연도를 전달하고 페이지 새로 고침
        const form = document.querySelector("form");
        const bakeryNoInput = document.querySelector("input[name='bakeryNo']");
        bakeryNoInput.value = bakeryNoInput.getAttribute('data-bakery-no'); // 필요한 경우 bakeryNo 값을 폼에 추가

        form.submit(); // 폼 제출
    });
});

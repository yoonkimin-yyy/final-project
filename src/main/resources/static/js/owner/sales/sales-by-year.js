document.addEventListener("DOMContentLoaded", () => {
    const yearSelect = document.getElementById("year-select");
    
    const availableYears = JSON.parse(yearSelect.getAttribute("data-available-years"));
    const currentYear = parseInt(yearSelect.getAttribute("data-current-year"), 10);


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

    populateYearOptions(availableYears, currentYear);

    yearSelect.addEventListener("change", (event) => {
        const selectedYear = event.target.value;

        const form = document.querySelector("form");
        const bakeryNoInput = document.querySelector("input[name='bakeryNo']");
        bakeryNoInput.value = bakeryNoInput.getAttribute('data-bakery-no'); 

        form.submit(); 
    });
});

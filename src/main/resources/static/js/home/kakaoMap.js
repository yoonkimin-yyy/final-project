// ✅ AJAX로 지역별 빵집 데이터 가져오기
function fetchBakeries(region) {
	
	const queries = ["빵집", "디저트", "카페","케이크","마카롱"]; // ✅ 자동 검색 키워드 목록
	
	var activeRegionButton = document.querySelector(".region-button-active");
	   if (!activeRegionButton) {
	       return;
	   }

	   var region = activeRegionButton.getAttribute("data-region"); 
	
	queries.forEach(query =>{
		
    $.ajax({
        url: "/bbanggil/bakeries",
        type: "GET",
        data: { query: query, region: region },
        dataType: "json",
        success: function(data) {
			console.log(data);
            updateMap(region, data);  // ✅ 가져온 데이터로 지도 업데이트
			updateRegionButton(region, data.length ,"개");  // ✅ 버튼에 빵집 개수 업데이트
        },
        error: function(error) {
            console.error("AJAX 요청 실패:", error);
        }
    });
	});
}

// ✅ 지역 버튼 클릭 이벤트 추가
document.querySelectorAll(".region-button").forEach(button => {
    button.addEventListener("click", function() {
        var region = this.getAttribute("data-region");

        // ✅ 버튼 활성화 효과
        document.querySelectorAll(".region-button").forEach(btn => btn.classList.remove("region-button-active"));
        this.classList.add("region-button-active");

        fetchBakeries(region);
    });
});

// ✅ 기본적으로 서울 지역 데이터 로드
document.addEventListener("DOMContentLoaded", function() {
    fetchBakeries("seoul");
});


// ✅ 지역 버튼에 빵집 개수 표시
function updateRegionButton(region, count) {
    document.querySelector(`.region-button[data-region="${region}"]`).innerText = `${regionName(region)} (${count})`;
}

// ✅ 지역 코드 → 한글 변환 (버튼 업데이트용)
function regionName(region) {
    const regionNames = {
        seoul: "서울", gyeonggi: "경기", incheon: "인천", busan: "부산",
        daegu: "대구", gwangju: "광주", daejeon: "대전", ulsan: "울산",
        sejong: "세종", gangwon: "강원", chungbuk: "충북", chungnam: "충남",
        jeonbuk: "전북", jeonnam: "전남", gyeongbuk: "경북", gyeongnam: "경남",
        jeju: "제주"
    };
    return regionNames[region] || region;
}



function fetchBakeriesByRegion() {
    const activeRegionButton = document.querySelector(".region-button-active");
    if (!activeRegionButton) return;

    const region = activeRegionButton.getAttribute("data-region");

    $.ajax({
        url: "/by-region",
        type: "GET",
        data: { region: region },
        dataType: "json",
        success: function (data) {
			console.log("✅ AJAX 응답 data:", data);
            updateMap(region, data);  // 지도 업데이트
            updateRegionButton(region, data.length, "개"); // 버튼 개수 업데이트
        },
        error: function (error) {
            console.error("빵집 데이터 조회 실패:", error);
        }
    });
}



//  지역 버튼에 빵집 개수 표시
function updateRegionButton(region, count) {
    document.querySelector(`.region-button[data-region="${region}"]`).innerText = `${regionName(region)} (${count})`;
}

//  지역 코드 → 한글 변환 (버튼 업데이트용)
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

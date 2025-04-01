package kr.kro.bbanggil.user.bakery.dto;


import java.util.ArrayList;
import java.util.List;

import kr.kro.bbanggil.user.bakery.dto.response.BakeryDetailResponseDto;
import kr.kro.bbanggil.user.bakery.dto.response.BakeryResponseScheduleDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class BakeryDto {
	private double no;  // DB에서 사용될 ID
    private String name;  // 빵집 이름
    private double latitude;  // 위도
    private double longitude;  // 경도
    private String region;  // 지역 (서울, 경기 등)
    private String address;  // 상세 주소
    private String phone;  // 전화번호
    
    // 추가 연관된 필드
    
    private BakeryDetailResponseDto  response = new BakeryDetailResponseDto();
    private List<BakeryResponseScheduleDto> schedule = new ArrayList<>();
    private List<BakeryImageDTO> images = new ArrayList<>();
    
    
    
    
}



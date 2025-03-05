package kr.kro.bbanggil.bakery.dto;

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
}

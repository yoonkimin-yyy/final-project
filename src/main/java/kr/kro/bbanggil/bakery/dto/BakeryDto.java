package kr.kro.bbanggil.bakery.dto;

import kr.kro.bbanggil.bakery.response.dto.BakeryResponseBakeryDetailDto;
import kr.kro.bbanggil.bakery.response.dto.BakeryResponseCartDto;
import kr.kro.bbanggil.bakery.response.dto.BakeryResponseImageDto;
import kr.kro.bbanggil.bakery.response.dto.BakeryResponseMenuDto;
import kr.kro.bbanggil.bakery.response.dto.BakeryResponseReviewDto;
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
    private BakeryResponseReviewDto review = new BakeryResponseReviewDto();  
    private BakeryResponseMenuDto menu = new BakeryResponseMenuDto();  
    private BakeryResponseImageDto image;
    private BakeryResponseBakeryDetailDto detail = new BakeryResponseBakeryDetailDto();
    private BakeryResponseCartDto cart = new BakeryResponseCartDto();

    
    
}



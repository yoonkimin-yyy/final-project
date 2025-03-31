package kr.kro.bbanggil.user.bakery.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 빵집에 대한 정보
@Getter
@Setter
@NoArgsConstructor
public class BakeryInfoDTO {
	private int	bakeryNo;
	private String bakeryName;
	private String bakeryAddress;
	private String bakeryPhone;
	private double bakeryLat;//위도
	private double bakeryLog;//경도
	private String bakeryRegion;
	private BakeryDetailDTO bakeryDetailDTO = new BakeryDetailDTO();
	private BakeryReviewDTO bakeryReviewDTO = new BakeryReviewDTO();
	private BakeryScheduleDTO bakeryScheduleDTO = new BakeryScheduleDTO();
	private List<BakeryImageDTO> bakeryImageDTO = new ArrayList<>();
	private int reviewCount;
	
}

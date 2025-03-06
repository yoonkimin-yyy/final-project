package kr.kro.bbanggil.bakery.dto;

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
	private double bakeryLat;
	private double bakeryLog;
	private String bakeryRegion;
	private BakeryDetailDTO bakeryDetailDTO = new BakeryDetailDTO();
	private BakeryReviewDTO bakeryReviewDTO = new BakeryReviewDTO();
	private BakeryScheduleDTO bakeryScheduleDTO = new BakeryScheduleDTO();
	
}

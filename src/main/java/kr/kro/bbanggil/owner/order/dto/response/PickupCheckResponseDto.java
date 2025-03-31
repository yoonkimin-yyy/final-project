package kr.kro.bbanggil.owner.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PickupCheckResponseDto {
	
	private int pickupStatusNo;
	private String pickupStatus;
	private String refusalDetail;
	
}

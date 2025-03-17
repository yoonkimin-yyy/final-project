package kr.kro.bbanggil.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PickupCheckRequestDto {
	
	private int pickupStatusNo;
	private String pickupStatus;
	private String refusalDetail;
	
}

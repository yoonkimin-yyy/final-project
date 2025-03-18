package kr.kro.bbanggil.pickup.response.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PickupStatusResponseDTO {

	private String pickupStatus;
	private String rejectionReason;
	
}

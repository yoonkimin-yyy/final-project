package kr.kro.bbanggil.owner.pickup.response.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PickupPaymentResponseDTO {

	private int orderNo;
	private String requestContent;
	private String paymentDate;
	private String amount;
	
}

package kr.kro.bbanggil.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {

	private String merchantUid;
	private int account;
	private String status;
	private String recipientsName;
	private String recipientsPhoneNum;
	private String requestContent;
	private String impUid;
	
}

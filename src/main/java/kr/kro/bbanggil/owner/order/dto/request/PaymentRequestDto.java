package kr.kro.bbanggil.owner.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentRequestDto {

	private String merchantUid;
	private int account;
	private String status;
	private String recipientsName;
	private String recipientsPhoneNum;
	private String requestContent;
	private String impUid;
	
}

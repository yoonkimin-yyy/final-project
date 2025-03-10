package kr.kro.bbanggil.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentDto {

	private String merchantUid;
	private int account;
	private String status;
	private String recipientsName;
	private String recipientsPhoneNum;
	private String requestContent;
	private String impUid;
	
}

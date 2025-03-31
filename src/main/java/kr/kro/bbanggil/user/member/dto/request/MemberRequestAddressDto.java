package kr.kro.bbanggil.user.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequestAddressDto {
	private int userNo;
	private String userPostcode;
	private String userCity;
	private String userDistrict;
	private String userStreet;
	private String userNumber;
	private String userDetailaddress;
	
	
}

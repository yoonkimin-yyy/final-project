package kr.kro.bbanggil.user.member.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SigninResponseDto {
	
	private int userNo;
    private String userId;
    private String userName;
    private String userPassword;
	
}

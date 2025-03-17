package kr.kro.bbanggil.member.model.dto.response;

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

package kr.kro.bbanggil.user.member.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MypageUserResponseDto {
	
	/**
	 * 유저 테이블
	 * 유저 번호
	 * 이름
	 * 아이디
	 * 비밀번호
	 * 이메일
	 * 생년월일
	 * 폰 번호
	 * 타입 (user,owner,admin)
	 * 계정 활성화 디폴트 값 N
	 * 계정 생성일 sysdate;
	 */
	private int userNo;
	
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	@Size(min = 2, max = 10, message = "이름은 2~10자 사이여야 합니다.")
	@Pattern(regexp = "^[가-힣]+$", message = "이름은 한글만 가능합니다.")
	private String userName;

	
	
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String userEmail;
	

	private String phoneNum;
	private String userType;
	private String userBan;
	private String createdDate;
	
}

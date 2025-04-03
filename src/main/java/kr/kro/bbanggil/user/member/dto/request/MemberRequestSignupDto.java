package kr.kro.bbanggil.user.member.dto.request;

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
public class MemberRequestSignupDto {
	
	private int userNo; // 번호
	
	// 이름
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	@Size(min = 2, max = 10, message = "이름은 2~10자 사이여야 합니다.")
	@Pattern(regexp = "^[가-힣]+$", message = "이름은 한글만 가능합니다.")
	private String userName; 
	
	// 아이디
	@NotBlank(message = "아이디는 필수 입력 값입니다.")
	@Size(min = 5, max = 20, message = "아이디는 5~20자 사이여야 합니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영어와 숫자만 포함해야 합니다.")
	private String userId; 
	
	// 비밀번호
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이여야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
		     message = "비밀번호는 대문자, 숫자, 특수문자를 최소 1개 이상 포함해야 합니다.")
	private String userPassword; 
	
	// 이메일
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	@Email(message = "올바른 이메일 형식이 아닙니다.")
	private String userEmail; 
	
	// 핸드폰번호
	@NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
	@Pattern(regexp = "^010\\d{4}\\d{4}$", message = "휴대폰 번호는 숫자 11자리만 입력 가능합니다.")
	private String userPhoneNum; 
	
	// 생년월일
	@NotBlank(message = "생년월일은 필수 입력 값입니다.")
	private String userBirthDate; 
	
	// 타입 (user,owner,admin)
	private String userType; 
	
	// 계정 활성화 (디폴트 값 N)
	private String userBan; 
	
	// 계정 생성일
	private String createdDate; 
	
	// 경고 여부
	private boolean alert; 
	
	
	
	// 주소테이블 Dto
	private MemberRequestAddressDto requestAddressDto = new MemberRequestAddressDto(); 
	
	// 사업자번호 Dto
	private MemberRequestBusinessNoDto businessNoDto = new MemberRequestBusinessNoDto();
	
	
}

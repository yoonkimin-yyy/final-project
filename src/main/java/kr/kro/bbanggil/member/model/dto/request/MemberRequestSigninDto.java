package kr.kro.bbanggil.member.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequestSigninDto {
	
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
	
}

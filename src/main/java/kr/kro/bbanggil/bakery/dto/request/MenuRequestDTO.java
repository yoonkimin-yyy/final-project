package kr.kro.bbanggil.bakery.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Valid
public class MenuRequestDTO {
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "특수문자는 사용할 수 없습니다.")
	private String menuName;
	
	@Pattern(regexp = "^[0-9]$", message="숫자만 입력해주세요")
	private String menuPrice;
	
	private String menuPopulity;
	private int menuCategory;
	FileRequestDTO fileRequestDTO = new FileRequestDTO();
}

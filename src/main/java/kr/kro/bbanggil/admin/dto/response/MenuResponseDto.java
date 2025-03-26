package kr.kro.bbanggil.admin.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuResponseDto {
	
	private String menuName;
	private String menuPrice;
	private String resourcesPath;
	private String changeName;

}

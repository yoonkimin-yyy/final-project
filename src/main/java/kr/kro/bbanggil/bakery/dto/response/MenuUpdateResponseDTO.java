package kr.kro.bbanggil.bakery.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuUpdateResponseDTO {
	private String menuName;
	private String menuPrice;
	private String populity;
	
	private String resourcesPath;


}

package kr.kro.bbanggil.bakery.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuRequestDTO {
	private String menuName;
	private String menuPrice;
	private String menuPopulity;
	private int menuCategory;
	FileRequestDTO fileRequestDTO = new FileRequestDTO();
}

package kr.kro.bbanggil.user.bakery.dto.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuUpdateResponseDTO {
	private String menuName;
	private String menuPrice;
	private String populity;
	private String category;
	
	private String resourcesPath;
	private String changeName;
	
	List<CategoryResponseDTO> menuCategory = new ArrayList<>();

}

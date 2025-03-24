package kr.kro.bbanggil.bakery.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MenuResponseDto {

	
	
	private int menuNo;
	private String menuName;
	private int menuPrice;
	private  String menuInfo;
	private int bakeryNo;
	private int categoryNo;
	private String category;
	
	
	
	private String changeName;
	private String orginalName;
	private String extension;
	private Long fileSize;
	private String localPath;
	private String resourcesPath ;
	String foldenamePath;
	
	private int imgNo;
	
	
	
	
}

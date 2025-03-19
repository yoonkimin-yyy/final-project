package kr.kro.bbanggil.bakery.response.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuResponseDto {

	
	
	private int menuNo;
	private String menuName;
	private int menuPrice;
	private  String menuInfo;
	private int bakeryNo;
	private int categoryNo;
	
	
	
	private String changeName;
	private String orginalName;
	private String extension;
	private Long fileSize;
	private String localPath;
	private String resourcePath;
	private String foldenamePath;
	
	
	
	
	
}

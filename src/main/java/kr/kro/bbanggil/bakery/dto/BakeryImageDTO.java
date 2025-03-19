package kr.kro.bbanggil.bakery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BakeryImageDTO {
	
	private int bakeryImgNo;
	private String changeName;
	private String originalName;
	private String extension;
	private String localPath;
	private String resourcesPath;
	private String folderNamePath;
	private String position;
	private int bakeryNo;
}

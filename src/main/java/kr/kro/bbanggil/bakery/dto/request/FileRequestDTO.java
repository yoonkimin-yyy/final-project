package kr.kro.bbanggil.bakery.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class FileRequestDTO {
	private int no;

	@Setter
	private String changeName;
	
	@Setter
	private String originalName;
	
	private String uploadDate;
	
	@Setter
	private String folderNamePath;
	
	@Setter
	private String extension;
	
	@Setter
	private long size;
	
	@Setter
	private String localResourcePath;
	
	@Setter
	private String localPath;
}

package kr.kro.bbanggil.bakery.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FileResponseDTO {
	private int no;
	private String changeName;
	private String originalName;
	private String uploadDate;
	private String folderNamePath;
	private String extension;
	private long size;
	private String resourcesPath;
}

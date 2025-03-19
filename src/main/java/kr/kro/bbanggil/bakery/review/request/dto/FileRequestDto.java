package kr.kro.bbanggil.bakery.review.request.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class FileRequestDto {

	private int no;

	@Setter
	private String changeName;

	@Setter
	private int reviewNo;
	
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

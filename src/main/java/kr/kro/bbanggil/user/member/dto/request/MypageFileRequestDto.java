package kr.kro.bbanggil.user.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MypageFileRequestDto {
	
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

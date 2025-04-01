package kr.kro.bbanggil.user.bakery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewImageDto {

	
	
	 	private int imgNo;                // 이미지 번호 (DB 식별용)
	    private String changeName;       // 저장된 파일명
	    private String originalName;     // 원본 파일명 (필요 시)
	    private String resourcesPath;    // 리소스 경로
	    private String localPath;        // 실제 파일 경로 (서버 내부 용도)

}

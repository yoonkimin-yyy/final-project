package kr.kro.bbanggil.bakery.response.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BakeryResponseImageDto {

	private int imgNo; // 이미지 번호
	private String changeName; // 저장된 파일명(변경된 이름)
	private String resourcesPath; // 웹에서 접근 가능한 이미지 경로
	
	
}

package kr.kro.bbanggil.user.bakery.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BakeryDetailResponseDto {

	
	private double totalCount;
	private String createDate;
	private int imgNo; // 이미지 번호
	private String changeName; // 저장된 파일명(변경된 이름)
	private String resourcesPath; // 웹에서 접근 가능한 이미지 경로
	private int menuNo; //메뉴번호
	private String menuName;// 메뉴 이름
	private Long menuPrice; // 메뉴 가격
	private String menuInfo; // 메뉴 정보
	private String categoryName; 
	private String category;
	private String reviewRating;
	
	private String amenity;
	private String insideInfo;
	private String outsideInfo;
	private String createdDate;
	private String submissionDate;
	private String position;
	
}

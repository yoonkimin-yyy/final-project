package kr.kro.bbanggil.admin.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminResponseDto {
	
	private String userName;
	private String userId;
	private String userPhoneNum;
	private String bakeryName;
	private String bakeryAddress;
	private String amenity;
	private String insideInfo;
	private String outsideInfo;
	private String agree;
	private String submissionDate;
	
	private List<BakeryImgResponseDto> bakeryImgPath;

}

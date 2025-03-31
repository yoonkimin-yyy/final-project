package kr.kro.bbanggil.admin.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class AdminResponseDto {
	
	private String userNo;
	private String userName;
	private String userId;
	private String userPhoneNum;
	private String userEmail;
	private String userBirthdate;
	private String userType;
	private String userCreatedate;
	private String userBusinessNo;
	private String agreeEmail;
	private String bakeryNo;
	private String bakeryName;
	private String bakeryAddress;
	private String amenity;
	private String insideInfo;
	private String outsideInfo;
	private String agree;
	private String submissionDate;
	private String acceptDate;
	
	private List<BakeryImgResponseDto> bakeryImgPath;
	
	// 신고 답변 DTO
	// 신고 당한 유저
	
	private int reviewNo;
	private int reportNo;
	private String reportDetail;
	private String reportDate;
	private String reportResult;
	private String reportResultDate;
	private String reportUnfreeze;
	private String unfreezeDetail;
	private String reviewDetail;
	private String reporter;
	private String reportedUser;
	
	@Setter
	private String answer;
	
	@Setter
	private String adminId;

}

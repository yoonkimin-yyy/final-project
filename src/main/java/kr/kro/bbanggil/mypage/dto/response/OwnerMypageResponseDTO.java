package kr.kro.bbanggil.mypage.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerMypageResponseDTO {
	private String bakeryName;
	private String bakeryAddress;
	private String bakeryPhone;
	private String userNo;
	private String agree;
	private String submissionDate;
}

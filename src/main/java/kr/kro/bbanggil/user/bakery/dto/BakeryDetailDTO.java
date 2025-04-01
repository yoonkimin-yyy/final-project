package kr.kro.bbanggil.user.bakery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BakeryDetailDTO {
	private int bakeryNo;
	private String bakeryExp;
	private String bakeryAmenity;
	private String bakeryInsideInfo;
	private String bakeryOutSideInfo;
	private String bakeryCreatedDate;
	private String bakerySubmissionDate;
}

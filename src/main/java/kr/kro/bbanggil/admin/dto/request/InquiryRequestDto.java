package kr.kro.bbanggil.admin.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InquiryRequestDto {
	
	private Integer userNo;
	private String inquiryTitle;
	private String inquiryContent;

}

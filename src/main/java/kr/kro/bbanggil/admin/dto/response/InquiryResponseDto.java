package kr.kro.bbanggil.admin.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InquiryResponseDto {

	 	private int inquiryNo;
	    private int userNo;
	    private String userName;
	    private String inquiryTitle;
	    private String inquiryContent;
	    private String isAnswered;
	    private String createDate;
	    private String email;

	    //  답변 정보 포함
	    private String replyContent;
	    private String replyDate;
	
}

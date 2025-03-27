package kr.kro.bbanggil.admin.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InquiryReplyRequestDto {

	 private int inquiryNo;            
	 private String replyContent;      
	 private String replyDate; 
	 private int adminNo;

	
	
	
}

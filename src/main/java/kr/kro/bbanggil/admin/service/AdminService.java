package kr.kro.bbanggil.admin.service;

import java.util.List;

<<<<<<< HEAD
import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
=======
import kr.kro.bbanggil.admin.dto.request.AdminEmailRequestDto;
>>>>>>> 44d6a0e0c72f2b4a2a2fa346f94a5bc2ebe65166
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;

public interface AdminService {

	List<AdminResponseDto> subList();

	List<AdminResponseDto> bakeryList();

	List<AdminResponseDto> userList();
	
	AdminResponseDto acceptList(int listNum);

	void update(String action, int listNum, String rejectReason);
	
	void saveInquiry(InquiryRequestDto inquiryRequestDto);
	
	List<InquiryResponseDto> getInquiryList();
	
	void saveAnswer(InquiryReplyRequestDto inquiryReplyDto);

	void sendEmail(AdminEmailRequestDto adminReqeustDto);

}

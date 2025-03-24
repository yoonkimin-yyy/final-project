package kr.kro.bbanggil.admin.service;

import java.util.List;

import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;

public interface AdminService {

	List<AdminResponseDto> subList();

	AdminResponseDto acceptList(int listNum);

	void update(String action, int listNum, String rejectReason);
	
	void saveInquiry(InquiryRequestDto inquiryRequestDto);
	
	List<InquiryResponseDto> getInquiryList();
	
	void saveAnswer(InquiryReplyRequestDto inquiryReplyDto);

}

package kr.kro.bbanggil.admin.service;

import java.util.List;


import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
import kr.kro.bbanggil.admin.dto.request.AdminEmailRequestDto;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;
import kr.kro.bbanggil.admin.dto.response.MenuResponseDto;

public interface AdminService {

	List<AdminResponseDto> subList();

	List<AdminResponseDto> bakeryList();

	List<AdminResponseDto> userList();

	AdminResponseDto bakeryDetailList(int bakeryNo, int userNo);

	AdminResponseDto userDetailList(int userNo);

	AdminResponseDto acceptList(int bakeryNo, int userNo);

	List<MenuResponseDto> menuList(int bakeryNo);
	
	void update(String action, int bakeryNo, String rejectReason);

	void sendEmail(AdminEmailRequestDto adminReqeustDto);

	void saveInquiry(InquiryRequestDto inquiryRequestDto);

	List<InquiryResponseDto> getInquiryList();

	void saveAnswer(InquiryReplyRequestDto inquiryReplyDto);

}

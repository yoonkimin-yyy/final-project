package kr.kro.bbanggil.admin.service;

import java.util.List;
import java.util.Map;

import kr.kro.bbanggil.admin.dto.request.AdminEmailRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;
import kr.kro.bbanggil.admin.dto.response.MonthlyOrderResponseDTO;

public interface AdminService {

	List<AdminResponseDto> subList();

	List<AdminResponseDto> bakeryList();

	List<AdminResponseDto> userList();
	
	AdminResponseDto bakeryDetailList(int listNum);
	
	AdminResponseDto userDetailList(int listNum);
	
	AdminResponseDto acceptList(int listNum);
	
	void update(String action, int listNum, String rejectReason);
	
	void saveInquiry(InquiryRequestDto inquiryRequestDto);
	
	List<InquiryResponseDto> getInquiryList();
	
	void saveAnswer(InquiryReplyRequestDto inquiryReplyDto);

	void sendEmail(AdminEmailRequestDto adminReqeustDto);

	Map<String, Object> trafficMonitoring();

	Map<String, Object> bottomContent();

	List<MonthlyOrderResponseDTO> getMonthlyOrderCount();

}

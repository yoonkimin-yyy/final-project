package kr.kro.bbanggil.admin.service;

import java.util.List;
import java.util.Map;

import kr.kro.bbanggil.admin.dto.request.AdminEmailRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
import kr.kro.bbanggil.admin.dto.request.ReportRequestDTO;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;
import kr.kro.bbanggil.admin.dto.response.MenuResponseDto;
import kr.kro.bbanggil.admin.dto.response.MonthlyOrderResponseDTO;

public interface AdminMainService {

	List<AdminResponseDto> subList();

	List<AdminResponseDto> bakeryList();

	List<AdminResponseDto> userList();

	void sendEmail(AdminEmailRequestDto adminReqeustDto);

	Map<String, Object> trafficMonitoring();

	Map<String, Object> bottomContent();

	List<MonthlyOrderResponseDTO> getMonthlyOrderCount();

	List<MenuResponseDto> categoryList();

	void addCategory(String newCategory);

	void deleteCategory(Map<String, List<String>> requestBody);

	List<AdminResponseDto> reportList();

	void saveInquiry(InquiryRequestDto inquiryRequestDto);

}

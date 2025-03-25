package kr.kro.bbanggil.admin.service;

import java.util.List;

import kr.kro.bbanggil.admin.dto.request.AdminEmailRequestDto;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;

public interface AdminService {

	List<AdminResponseDto> subList();

	List<AdminResponseDto> bakeryList();

	List<AdminResponseDto> userList();
	
	AdminResponseDto bakeryDetailList(int bakeryNo, int userNo);
	
	AdminResponseDto userDetailList(int userNo);
	
	AdminResponseDto acceptList(int bakeryNo, int userNo);
	
	void update(String action, int bakeryNo, String rejectReason);

	void sendEmail(AdminEmailRequestDto adminReqeustDto);

}

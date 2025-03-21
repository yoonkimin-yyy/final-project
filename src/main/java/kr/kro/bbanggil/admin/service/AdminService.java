package kr.kro.bbanggil.admin.service;

import java.util.List;

import kr.kro.bbanggil.admin.dto.request.AdminEmailRequestDto;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;

public interface AdminService {

	List<AdminResponseDto> subList();

	List<AdminResponseDto> bakeryList();

	List<AdminResponseDto> userList();
	
	AdminResponseDto acceptList(int listNum);

	void update(String action, int listNum, String rejectReason);

	void sendEmail(AdminEmailRequestDto adminReqeustDto);

}

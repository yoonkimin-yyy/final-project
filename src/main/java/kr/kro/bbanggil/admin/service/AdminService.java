package kr.kro.bbanggil.admin.service;

import java.util.List;

import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;

public interface AdminService {

	List<AdminResponseDto> subList();

	AdminResponseDto acceptList(int listNum);

	void update(String action, int listNum, String rejectReason);

}

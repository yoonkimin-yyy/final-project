package kr.kro.bbanggil.admin.service;

import java.util.List;

import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.MenuResponseDto;
import kr.kro.bbanggil.admin.dto.response.myBakeryResponseDTO;

public interface AdminBakeryService {

	AdminResponseDto bakeryDetailList(int bakeryNo, int userNo);

	AdminResponseDto acceptList(int bakeryNo, int userNo);

	List<MenuResponseDto>menuList(int bakeryNo);

	void update(String action, int bakeryNo, String rejectReason);

	void delete(int bakeryNo);

}

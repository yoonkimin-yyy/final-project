package kr.kro.bbanggil.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.MenuResponseDto;
import kr.kro.bbanggil.admin.mapper.AdminBakeryMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminBakeryServiceImpl implements AdminBakeryService{
	private final AdminBakeryMapper adminBakeryMapper;
	
	
	@Override
	public AdminResponseDto bakeryDetailList(int bakeryNo, int userNo) {
		return adminBakeryMapper.bakeryDetailList(bakeryNo, userNo);
	}
	
	@Override
	public AdminResponseDto acceptList(int bakeryNo, int userNo) {
		return adminBakeryMapper.acceptList(bakeryNo, userNo);
	}

	@Override
	public List<MenuResponseDto> menuList(int bakeryNo) {
		return adminBakeryMapper.menuList(bakeryNo);
	}
	
	@Override
	public void update(String action, int bakeryNo, String rejectReason) {
		adminBakeryMapper.update(action, bakeryNo, rejectReason);
	}
}

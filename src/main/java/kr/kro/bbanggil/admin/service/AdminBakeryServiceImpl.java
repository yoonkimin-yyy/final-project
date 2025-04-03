package kr.kro.bbanggil.admin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.MenuResponseDto;
import kr.kro.bbanggil.admin.mapper.AdminMapper;
import kr.kro.bbanggil.global.exception.NoMenuFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminBakeryServiceImpl implements AdminBakeryService{
	private final AdminMapper adminMapper;
	
	@Override
	public AdminResponseDto bakeryDetailList(int bakeryNo, int userNo) {
		return adminMapper.bakeryDetailList(bakeryNo, userNo);
	}
	
	@Override
	public AdminResponseDto acceptList(int bakeryNo, int userNo) {
		return adminMapper.acceptList(bakeryNo, userNo);
	}

	@Override
	public List<MenuResponseDto> menuList(int bakeryNo) {
		
		List<MenuResponseDto> menuList = adminMapper.menuList(bakeryNo);
		
		Optional<List<MenuResponseDto>> optionalMenuList = Optional.ofNullable(menuList);
		
		optionalMenuList.filter(list -> !list.isEmpty())
        				.orElseThrow(() -> new NoMenuFoundException("등록된 메뉴가 없습니다."));
		
		return menuList;
	}
	
	@Override
	public void update(String action, int bakeryNo, String rejectReason) {
		adminMapper.update(action, bakeryNo, rejectReason);
	}
	
	@Override
	public void delete(int bakeryNo) {
		adminMapper.deleteBakery(bakeryNo);
	}
	
}

package kr.kro.bbanggil.admin.service;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService{

	private final AdminMapper adminMapper;
	
	@Override
	public AdminResponseDto userDetailList(int userNo) {
		return adminMapper.userDetailList(userNo);
	}
}

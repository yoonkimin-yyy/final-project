package kr.kro.bbanggil.admin.service;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;

@Service
public class AdminUserServiceImpl implements AdminUserService{

	@Override
	public AdminResponseDto userDetailList(int userNo) {
		return adminMapper.userDetailList(userNo);
	}
}

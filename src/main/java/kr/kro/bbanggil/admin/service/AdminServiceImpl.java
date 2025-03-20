package kr.kro.bbanggil.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.mapper.AdminMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AdminMapper adminMapper;
	
	@Override
	public List<AdminResponseDto> subList() {
		return adminMapper.subList();
	}
	
	@Override
	public List<AdminResponseDto> bakeryList() {
		return adminMapper.bakeryList();
	}
	
	@Override
	public List<AdminResponseDto> userList() {
		return adminMapper.userId();
	}
	
	@Override
	public AdminResponseDto acceptList(int listNum) {
		return adminMapper.acceptList(listNum);
	}
	
	@Override
	public void update(String action, int listNum, String rejectReason) {
		adminMapper.update(action, listNum, rejectReason);
	}
	
}

package kr.kro.bbanggil.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.admin.dto.AdminDto;
import kr.kro.bbanggil.admin.mapper.AdminMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AdminMapper adminMapper;
	
	@Override
	public List<AdminDto> list(AdminDto adminDto) {
		return adminMapper.list(adminDto);
	}
	
}

package kr.kro.bbanggil.admin.service;

import java.util.List;


import org.springframework.stereotype.Service;

import jakarta.validation.constraints.Email;
import kr.kro.bbanggil.admin.dto.request.AdminEmailRequestDto;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.mapper.AdminMapper;
import kr.kro.bbanggil.email.service.EmailServiceImpl;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final EmailServiceImpl emailServiceImpl;
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
	public AdminResponseDto bakeryDetailList(int listNum) {
		return adminMapper.bakeryDetailList(listNum);
	}
	
	@Override
	public AdminResponseDto acceptList(int listNum) {
		return adminMapper.acceptList(listNum);
	}
	
	@Override
	public void update(String action, int listNum, String rejectReason) {
		adminMapper.update(action, listNum, rejectReason);
	}

	@Override
	public void sendEmail(AdminEmailRequestDto adminRequestDto) {

		String[] addresses = adminRequestDto.getAddress().split("\\s*,\\s*"); // 정규식
		String title = adminRequestDto.getTitle();
		String content = adminRequestDto.getContent();
		
		for(int i=0; i<addresses.length; i++) {
			emailServiceImpl.sendEmail(addresses[i], title, content);
		}
	}

}

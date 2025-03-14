package kr.kro.bbanggil.admin.service;

import java.util.List;

import kr.kro.bbanggil.admin.dto.AdminDto;

public interface AdminService {

	List<AdminDto> list(AdminDto adminDto);

}

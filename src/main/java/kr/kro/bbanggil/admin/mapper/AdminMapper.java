package kr.kro.bbanggil.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.kro.bbanggil.admin.dto.AdminDto;

@Mapper
public interface AdminMapper {

	List<AdminDto> list(AdminDto adminDto);

}

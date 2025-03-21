package kr.kro.bbanggil.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;

@Mapper
public interface AdminMapper {

	List<AdminResponseDto> subList();

	List<AdminResponseDto> bakeryList();

	List<AdminResponseDto> userId();
	
	AdminResponseDto acceptList(int listNum);

	void update(@Param("action") String action,
				@Param("listNum") int listNum,
				@Param("rejectReason") String rejectReason);
	
}

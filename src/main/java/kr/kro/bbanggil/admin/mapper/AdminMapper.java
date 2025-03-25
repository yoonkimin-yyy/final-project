package kr.kro.bbanggil.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;

@Mapper
public interface AdminMapper {

	List<AdminResponseDto> subList();

	List<AdminResponseDto> bakeryList();

	List<AdminResponseDto> userList();
	
	AdminResponseDto bakeryDetailList(@Param("bakeryNo") int bakeryNo, 
		    						  @Param("userNo") int userNo);
	
	AdminResponseDto userDetailList(int userNo);
	
	AdminResponseDto acceptList(@Param("bakeryNo") int bakeryNo, 
							    @Param("userNo") int userNo);

	void update(@Param("action") String action,
				@Param("bakeryNo") int listNum,
				@Param("rejectReason") String rejectReason);

}

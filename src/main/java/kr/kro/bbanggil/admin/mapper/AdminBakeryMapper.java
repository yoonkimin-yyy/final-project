package kr.kro.bbanggil.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.MenuResponseDto;

@Mapper
public interface AdminBakeryMapper {
	
	AdminResponseDto bakeryDetailList(@Param("bakeryNo") int bakeryNo, 
									  @Param("userNo") int userNo);
	
	AdminResponseDto acceptList(@Param("bakeryNo") int bakeryNo, 
							    @Param("userNo") int userNo);

	List<MenuResponseDto> menuList(int bakeryNo);
	
	void update(@Param("action") String action,
				@Param("bakeryNo") int listNum,
				@Param("rejectReason") String rejectReason);

}

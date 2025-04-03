package kr.kro.bbanggil.user.bakery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.user.bakery.dto.request.MenuRequestDTO;
import kr.kro.bbanggil.user.bakery.dto.response.CategoryResponseDTO;
import kr.kro.bbanggil.user.bakery.dto.response.FileResponseDTO;
import kr.kro.bbanggil.user.bakery.dto.response.MenuResponseDto;
import kr.kro.bbanggil.user.bakery.dto.response.MenuUpdateResponseDTO;

@Mapper
public interface MenuMapper {

	public List<CategoryResponseDTO> getCategory();

	public void menuInsert(@Param("menuRequestDTO")MenuRequestDTO menuDTO, 
						   @Param("bakeryNo")int bakeryNo);

	public void menuFileUpload(MenuRequestDTO menuDTO);

	public String getMenuImgInfo(int menuNo);

	public void deleteMenuImg(String changeName);

	public void menuDelete(int menuNo);

	public MenuUpdateResponseDTO getMenuUpdate(int menuNo);

	public FileResponseDTO getMenuImg(int menuNo);

	public void menuUpdate(MenuRequestDTO menuDTO);

	public List<MenuResponseDto> getMenuList(int bakeryNo);

	public String getBakeryName(int bakeryNo);

}

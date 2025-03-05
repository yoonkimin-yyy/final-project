package kr.kro.bbanggil.bakery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.kro.bbanggil.bakery.dto.request.FileRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.MenuRequestDTO;
import kr.kro.bbanggil.bakery.dto.response.CategoryResponseDTO;
import kr.kro.bbanggil.bakery.dto.response.MenuResponseDTO;

@Mapper
public interface BakeryMapper {

	void menuInsert(MenuRequestDTO menuRequestDTO);

	List<MenuResponseDTO> menuList(MenuRequestDTO menuRequestDTO);

	void menuFileUpload(FileRequestDTO fileRequestDTO);

	List<CategoryResponseDTO> getCategory();

}

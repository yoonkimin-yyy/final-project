package kr.kro.bbanggil.bakery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.bakery.dto.BakeryTimeSetDTO;
import kr.kro.bbanggil.bakery.dto.request.BakeryInsertRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.FileRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.MenuRequestDTO;
import kr.kro.bbanggil.bakery.dto.response.CategoryResponseDTO;
import kr.kro.bbanggil.bakery.dto.response.MenuResponseDTO;
import kr.kro.bbanggil.bakery.vo.BakeryDetailVO;
import kr.kro.bbanggil.bakery.vo.BakeryInfoVO;

@Mapper
public interface BakeryMapper {

	void menuInsert(MenuRequestDTO menuRequestDTO);

	List<MenuResponseDTO> menuList(MenuRequestDTO menuRequestDTO);

	void menuFileUpload(FileRequestDTO fileRequestDTO);

	List<CategoryResponseDTO> getCategory();

	void bakeryFileUpload(BakeryInsertRequestDTO bakeryRequestDTO);

	void bakeryInsert(BakeryInfoVO bakeryVO);

	void bakeryDetailInsert(BakeryDetailVO detailVO);

	void bakeryScheduleInsert(@Param("timeDTO") BakeryTimeSetDTO item,
							  @Param("bakeryNo") int no);


//	void bakeryScheduleInsert(BakeryTimeRequestDTO timeDTO);

}

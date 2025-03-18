package kr.kro.bbanggil.bakery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import kr.kro.bbanggil.bakery.dto.BakeryInfoDTO;
import kr.kro.bbanggil.bakery.dto.BakerySearchDTO;
import kr.kro.bbanggil.common.dto.PageInfoDTO;

import kr.kro.bbanggil.bakery.dto.BakeryTimeSetDTO;
import kr.kro.bbanggil.bakery.dto.request.BakeryInsertRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.FileRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.MenuRequestDTO;
import kr.kro.bbanggil.bakery.dto.response.CategoryResponseDTO;
import kr.kro.bbanggil.bakery.vo.BakeryDetailVO;
import kr.kro.bbanggil.bakery.vo.BakeryInfoVO;
import kr.kro.bbanggil.bakery.dto.BakeryDto;


@Mapper
public interface BakeryMapper {


	
	public List<BakeryInfoDTO> bakeryList(@Param("pi") PageInfoDTO pi,
										  @Param("day") String day,
										  @Param("orderType")String orderType,
										  @Param("bakerySearchDTO")BakerySearchDTO bakerySearchDTO);
	
	public int totalCount(BakerySearchDTO bakerySearchDTO);
	
	public List<BakeryInfoDTO> bakeryImage(@Param("bakeryNo") int bakeryNo);
	

	


	void menuInsert(@Param("menuRequestDTO")MenuRequestDTO menuRequestDTO, 
					@Param("bakeryNo")int bakeryNo);


	void menuFileUpload(MenuRequestDTO menuRequestDTO);

	List<CategoryResponseDTO> getCategory();

	void bakeryFileUpload(BakeryInsertRequestDTO bakeryRequestDTO);

	void bakeryInsert(BakeryInfoVO bakeryVO);

	void bakeryDetailInsert(BakeryDetailVO detailVO);

	void bakeryScheduleInsert(@Param("timeDTO") BakeryTimeSetDTO item,
							  @Param("bakeryNo") int no);


	void insertBakery(BakeryDto bakery); // 카카오 에서 데이터 주입 받기
	List<BakeryDto> getBakeriesByRegion(@Param("region") String region); // 지역에 따른 빵집 마커 select
	
	List<BakeryDto> getPopularBakeries(); // 인기빵집 select
	List<BakeryDto> getRecentBakeries();
	List<BakeryDto>getCategoryBakeries(@Param("categoryNames") List<String> categoryName);
	List<BakeryDto> getTopFiveOrders(); 
	
	
	List<BakeryDto> findBakeryImages(@Param("no") double No);

	int getBakeryNo();

	int getCurrentBakeryNo();



	void imgInsert(FileRequestDTO fileDTO);

	void setBakery(@Param("bakeryNo")int bakeryNo,
				   @Param("userNo")int userNo);


	

}

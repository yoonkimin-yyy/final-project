package kr.kro.bbanggil.bakery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.kro.bbanggil.bakery.dto.BakeryDto;
import kr.kro.bbanggil.bakery.dto.BakeryTimeSetDTO;
import kr.kro.bbanggil.bakery.dto.request.BakeryRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.FileRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.MenuRequestDTO;
import kr.kro.bbanggil.bakery.dto.response.CategoryResponseDTO;
import kr.kro.bbanggil.bakery.dto.response.FileResponseDTO;
import kr.kro.bbanggil.bakery.dto.response.bakeryUpdateResponseDTO;
import kr.kro.bbanggil.bakery.vo.BakeryDetailVO;
import kr.kro.bbanggil.bakery.vo.BakeryInfoVO;

@Mapper
public interface BakeryMapper {

	void menuInsert(@Param("menuRequestDTO")MenuRequestDTO menuRequestDTO, 
					@Param("bakeryNo")int bakeryNo);


	void menuFileUpload(MenuRequestDTO menuRequestDTO);

	List<CategoryResponseDTO> getCategory();

	void bakeryFileUpload(BakeryRequestDTO bakeryRequestDTO);

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


	void setBakery(@Param("bakeryNo")int bakeryNo,
				   @Param("userNo")int userNo);


	bakeryUpdateResponseDTO getBakeryInfo(int bakeryNo);

	List<FileRequestDTO> getBakeryImg(int bakeryNo);

	List<BakeryTimeSetDTO> getBakerySchedule(int bakeryNo);


	int requestUserNo(int bakeryNo);

	List<FileResponseDTO> getFileInfo(String imgLocation);


	void deleteFile(String fileName);


	void bakeryUpdate(BakeryRequestDTO bakeryRequestDTO);


	void bakeryDetailUpdate(BakeryRequestDTO bakeryRequestDTO);


	void bakeryScheduleUpdate(@Param("timeDTO")BakeryTimeSetDTO item, 
							  @Param("bakeryNo")int bakeryNo);


	void bakeryAccessUpdate(BakeryRequestDTO bakeryRequestDTO);

	
}

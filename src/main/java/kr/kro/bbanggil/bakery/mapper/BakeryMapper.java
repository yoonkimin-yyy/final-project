package kr.kro.bbanggil.bakery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import kr.kro.bbanggil.bakery.dto.BakeryDto;
import kr.kro.bbanggil.bakery.request.dto.MenuRequestDto;
import kr.kro.bbanggil.bakery.response.dto.MenuResponseDto;
import kr.kro.bbanggil.bakery.review.response.dto.ReviewResponseDto;

@Mapper
public interface BakeryMapper {

	void insertBakery(BakeryDto bakery); // 카카오 에서 데이터 주입 받기
	List<BakeryDto> getBakeriesByRegion(@Param("region") String region); // 지역에 따른 빵집 마커 select
	
	List<BakeryDto> getPopularBakeries(); // 인기빵집 select
	List<BakeryDto> getRecentBakeries();
	List<BakeryDto>getCategoryBakeries(@Param("categoryNames") List<String> categoryName);
	List<BakeryDto> getTopFiveOrders(); 
	
	
	List<BakeryDto> findBakeryImages(@Param("no") double No);
	List<BakeryDto> findBakeriesInfo(@Param("no") double No);
	List<MenuResponseDto> getMenuInfo(@Param("no") double No);
	List<BakeryDto> getBakeryDetail(@Param("no") double No);
	public List<ReviewResponseDto> getReviewImages(@Param("bakeryNo") double bakeryNo);

	
	
	void insertCartInfo(int cartNo, int menuNo, int menuCount);
	Integer getCartNoByUserNo(int userNo);
	Integer getLastCartNo();
	void insertCart(int userNo);
	BakeryDto findBakeryByNo(@Param("no")double bakeryNo);
	
	
	
}

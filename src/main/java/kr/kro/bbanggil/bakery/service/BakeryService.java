package kr.kro.bbanggil.bakery.service;

import java.util.List;


import kr.kro.bbanggil.bakery.dto.BakeryDto;
import kr.kro.bbanggil.bakery.request.dto.MenuRequestDto;
import kr.kro.bbanggil.bakery.response.dto.MenuResponseDto;

public interface BakeryService {

	
	
	public void saveBakery(BakeryDto bakery);
	List<BakeryDto> getBakeriesByRegion(String region);
	
	List<BakeryDto> getPopularBakeries();
	List<BakeryDto> getRecentBakeries();
	List<BakeryDto>getCategoryBakeries(List<BakeryDto> topBakeries);
	List<BakeryDto> getTopFiveOrders();
	
	
	List<BakeryDto> getBakeryImages(double no);
	
	List<BakeryDto> getBakeriesInfo(double no);
	
	List<MenuResponseDto> getMenuInfo(double no);
	
	void addCart(int userNo, List<MenuRequestDto> menuDto);
	
	
	BakeryDto getBakeryByNo(double bakeryNo);
	
}

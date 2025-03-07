package kr.kro.bbanggil.bakery.service;

import java.util.List;
import java.util.Map;

import kr.kro.bbanggil.bakery.dto.BakeryDto;

public interface BakeryService {

	
	
	public void saveBakery(BakeryDto bakery);
	List<BakeryDto> getBakeriesByRegion(String region);
	
	List<BakeryDto> getPopularBakeries();
	List<BakeryDto> getRecentBakeries();
	List<BakeryDto>getCategoryBakeries(List<BakeryDto> topBakeries);
	List<BakeryDto> getTopFiveOrders();
}

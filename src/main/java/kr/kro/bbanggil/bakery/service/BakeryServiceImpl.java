package kr.kro.bbanggil.bakery.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.bakery.dto.BakeryDto;
import kr.kro.bbanggil.bakery.mapper.BakeryMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BakeryServiceImpl implements BakeryService {

	
	private final BakeryMapper bakeryMapper;
	
	
	@Override
	public void saveBakery(BakeryDto bakery) {
		
		bakeryMapper.insertBakery(bakery);
		
	}
	@Override
	public List<BakeryDto> getBakeriesByRegion(String region){
	
		
		return bakeryMapper.getBakeriesByRegion(region);
	}
	@Override
	public List<BakeryDto> getPopularBakeries(){
		
		return bakeryMapper.getPopularBakeries();
	}
	@Override
	public List<BakeryDto> getRecentBakeries(){
		
		return bakeryMapper.getRecentBakeries();
	}
	@Override
	public List<BakeryDto>getCategoryBakeries(List<BakeryDto> topBakeries){
		
		List<String> categoryNames= topBakeries.stream()
				.map(bakery -> bakery.getMenu().getCategory())
				.distinct()
				.collect(Collectors.toList());
		
		 return bakeryMapper.getCategoryBakeries(categoryNames);
	}
	@Override
	public List<BakeryDto> getTopFiveOrders(){
		return bakeryMapper.getTopFiveOrders();
	}
		
	
	
	
}

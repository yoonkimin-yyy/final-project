package kr.kro.bbanggil.bakery.service;

import java.util.List;

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
	
	
}

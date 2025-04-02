package kr.kro.bbanggil.home.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.kro.bbanggil.user.bakery.dto.BakeryDto;
import kr.kro.bbanggil.user.bakery.service.BakeryServiceImpl;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class ApiHomeController {

	private final Logger logger = LogManager.getLogger(ApiHomeController.class);
	private final BakeryServiceImpl bakeryService;
	
	@GetMapping("by-region")
	
	public List<BakeryDto> getBakeriesByRegion(@RequestParam("region") String region){
		
		logger.info("by-region: '{}'", region);
		
		return bakeryService.getBakeriesByRegion(region);
		
	}
	
	
}

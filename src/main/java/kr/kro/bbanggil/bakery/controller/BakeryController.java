package kr.kro.bbanggil.bakery.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.kro.bbanggil.bakery.dto.BakeryDto;
import kr.kro.bbanggil.bakery.service.BakeryServiceImpl;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/bakery")
@AllArgsConstructor
public class BakeryController {

	private final BakeryServiceImpl bakeryService;
	
	
	
	
	
	@GetMapping("/list")
	public String list() {
		return "user/bakery-list";
	}
	
	@GetMapping("/insert/form")
	public String bakeryInsertForm() {
		return "owner/bakery-insert";
	}
	@GetMapping("/menu/insert/form")
	public String menuInsertForm() {
		return "owner/menu-insert";
		
	}
	@GetMapping("/detail/form")
	public String detail() {
		return "user/bakery-detail";
	}
	
	@GetMapping("/detail/{bakeryNo}")
	public String getBakeryImages(@PathVariable("bakeryNo") double no, Model model) {
		
		/**
		 * 가게 정보 가져오는 기능
		 */
	    List<BakeryDto> bakeriesInfo = bakeryService.getBakeryImages(no); 
	    model.addAttribute("bakeriesInfo", bakeriesInfo);
	    
	    return "user/bakery-detail"; // bakeryDetail.html 뷰 반환
	}
	
	
	
	
	
}

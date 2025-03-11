package kr.kro.bbanggil.bakery.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import kr.kro.bbanggil.bakery.dto.BakeryDto;
import kr.kro.bbanggil.bakery.dto.request.BakeryInsertImgRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.BakeryInsertRequestDTO;
import kr.kro.bbanggil.bakery.service.BakeryService;
import kr.kro.bbanggil.bakery.service.BakeryServiceImpl;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/bakery")
@AllArgsConstructor
public class BakeryController {
	private final BakeryService service;
	private final BakeryServiceImpl bakeryService;
	
	
	
	
	
	@GetMapping("/list")
	public String list() {
		return "user/bakery-list";
	}
	
	@GetMapping("/insert/form")
	public String bakeryInsertForm(BakeryInsertRequestDTO BakeryRequestDTO,
								   Model model) {
		model.addAttribute(BakeryRequestDTO);
		model.addAttribute("closeWindow", true);
		return "owner/bakery-insert";
	}
	/**
	 * 
	 * @param BakeryRequestDTO : insert에 대한 전반적인 데이터가 들어있는 DTO
	 * @param BakeryImgRequestDTO : insert에 필요한 이미지들을 포함하는 DTO
	 * dateSet() : weekday, weekend 데이터 입력 시 각 요일에 맞게 데이터를 넣어주는 메서드
	 * timeSet() : 각 요일에 opentime, closetime를 설정해주는 메서드
	 */
	@PostMapping("/insert")
	public String bakeryInsert(@ModelAttribute@Valid BakeryInsertRequestDTO BakeryRequestDTO,
							   @ModelAttribute BakeryInsertImgRequestDTO BakeryImgRequestDTO,
							   Model model) throws Exception {
		BakeryRequestDTO.setTime();
		service.bakeryInsert(BakeryRequestDTO,BakeryImgRequestDTO);
		
		return "common/home";
	}
//	@GetMapping("/menu/insert/form")
//	public String menuInsertForm(Model model) {
//		List<CategoryResponseDTO> category = service.getCategory();
//
//		model.addAttribute("category",category);
//		return "owner/menu-insert";
//		
//	}
	
	
	@GetMapping("/detail/form")
	public String detail() {
		return "user/bakery-detail";
	}
	
	@GetMapping("/detail/{bakeryNo}")
	public String getBakeryImages(@PathVariable("bakeryNo") double no, Model model) {
		
		/**
		 * 가게 정보 가져오는 기능
		 */
	    List<BakeryDto> bakeriesInfo = service.getBakeryImages(no); 
	    model.addAttribute("bakeriesInfo", bakeriesInfo);
	    
	    return "user/bakery-detail"; // bakeryDetail.html 뷰 반환
	}
	
	
	@GetMapping("/error")
	public String errorTest() {
		return "/common/error";
	}
	
	
}

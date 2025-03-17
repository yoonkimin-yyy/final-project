package kr.kro.bbanggil.bakery.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.validation.Valid;
import kr.kro.bbanggil.bakery.dto.BakeryDto;
import kr.kro.bbanggil.bakery.dto.request.BakeryImgRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.BakeryRequestDTO;
import kr.kro.bbanggil.bakery.dto.response.bakeryUpdateResponseDTO;
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
	public String bakeryInsertForm(BakeryRequestDTO BakeryRequestDTO,
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
	public String bakeryInsert(@ModelAttribute @Valid BakeryRequestDTO BakeryRequestDTO,
							   @ModelAttribute BakeryImgRequestDTO BakeryImgRequestDTO,
							   Model model) throws Exception {
		int userNo = 3;
		BakeryRequestDTO.setTime();
		service.bakeryInsert(BakeryRequestDTO,BakeryImgRequestDTO,userNo);
		
		return "common/home";
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
	    List<BakeryDto> bakeriesInfo = service.getBakeryImages(no); 
	    model.addAttribute("bakeriesInfo", bakeriesInfo);
	    
	    return "user/bakery-detail"; // bakeryDetail.html 뷰 반환
	}
	
	@GetMapping("/update/form")
	public String bakeryUpdateForm(@RequestParam(name="bakeryNo",required=false) Integer bakeryNo,Model model) {
		bakeryUpdateResponseDTO result = service.getbakeryInfo(bakeryNo);
		model.addAttribute("bakery",result);
		return "owner/bakery-update";
	}
	
	@PostMapping("/update")
	public String bakeryUpdate(BakeryRequestDTO bakeryRequestDTO,
							   BakeryImgRequestDTO bakeryImgRequestDTO
							   ) {
		int no = 33;
		service.bakeryUpdate(bakeryRequestDTO,bakeryImgRequestDTO,no);
		return "/owner/owner-mypage";
	}
	
	
}

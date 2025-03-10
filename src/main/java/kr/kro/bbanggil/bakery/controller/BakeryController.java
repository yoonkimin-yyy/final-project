package kr.kro.bbanggil.bakery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.kro.bbanggil.bakery.dto.request.BakeryInsertImgRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.BakeryInsertRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.MenuRequestDTO;
import kr.kro.bbanggil.bakery.dto.response.CategoryResponseDTO;
import kr.kro.bbanggil.bakery.dto.response.MenuResponseDTO;
import kr.kro.bbanggil.bakery.service.BakeryService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/bakery")
public class BakeryController {
	private final BakeryService service;

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
	@PostMapping("/insert")
	public String bakeryInsert(@ModelAttribute BakeryInsertRequestDTO BakeryRequestDTO,
							   @ModelAttribute BakeryInsertImgRequestDTO BakeryImgRequestDTO,
							   Model model) throws Exception {
		BakeryRequestDTO.getTimeDTO().dateSet();
		BakeryRequestDTO.getTimeDTO().timeSet();
		service.bakeryInsert(BakeryRequestDTO,BakeryImgRequestDTO);
		
		return "common/home";
	}
	@GetMapping("/menu/insert/form")
	public String menuInsertForm(Model model) {
		List<CategoryResponseDTO> category = service.getCategory();

		model.addAttribute("category",category);
		return "owner/menu-insert";
	}
	@PostMapping("menu/insert")
	@ResponseBody
	public ResponseEntity<Map<String,String>> menuInsert(MenuRequestDTO menuRequestDTO,
			 				 @RequestParam("menuImage") MultipartFile file,
			 				 Model model) {
		
		MenuResponseDTO result = service.menuInsert(menuRequestDTO,file);
		
		Map<String, String> response =new HashMap<>();
		response.put("message","success");
		
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
		
	}
}

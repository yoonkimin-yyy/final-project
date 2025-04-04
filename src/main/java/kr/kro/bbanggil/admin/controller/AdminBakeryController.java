package kr.kro.bbanggil.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.MenuResponseDto;
import kr.kro.bbanggil.admin.service.AdminBakeryService;
import kr.kro.bbanggil.global.exception.NoMenuFoundException;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/bakery")
@RequiredArgsConstructor
public class AdminBakeryController {
	private final AdminBakeryService adminBakeryService;

	@GetMapping("/detail")
	public String bakeryDetailForm(@RequestParam("bakeryNo") int bakeryNo,
			   					   @RequestParam("userNo") int userNo,
			   					   Model model) {

		AdminResponseDto result = adminBakeryService.bakeryDetailList(bakeryNo, userNo);
		
		model.addAttribute("result", result);

		return "admin/bakery-detail";
	}
	
	@GetMapping("/accept")
	public String bakeryAcceptForm(@RequestParam("listNum") int listNum,
								   @RequestParam("bakeryNo") int bakeryNo,
								   @RequestParam("userNo") int userNo,
								   Model model) {
		
		AdminResponseDto result = adminBakeryService.acceptList(bakeryNo, userNo);
		List<MenuResponseDto> menuList = adminBakeryService.menuList(bakeryNo);

    model.addAttribute("result", result);
		model.addAttribute("listNum", listNum);
		model.addAttribute("menuList", menuList);

		return "admin/bakery-accept";
	}
	

	@PostMapping("/update")
	public String bakeryUpdateForm(@RequestParam("action") String action,
								   @RequestParam("bakeryNo") int bakeryNo,
								   @RequestParam("rejectReason") String rejectReason) {
		
		adminBakeryService.update(action, bakeryNo, rejectReason);
		
		return "redirect:/admin/form";
	}
	
	@GetMapping("/delete")
	public String bakeryDelete(@RequestParam("bakeryNo") int bakeryNo) {
		adminBakeryService.delete(bakeryNo);
		
		return "redirect:/admin/form";
		
	}
	
	
}

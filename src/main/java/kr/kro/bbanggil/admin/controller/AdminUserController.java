package kr.kro.bbanggil.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.service.AdminUserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController {
	private final AdminUserService adminUserService;
	
	@GetMapping("/detail")
	public String userDetailForm(@RequestParam("userNo") int userNo,
								 Model model) {
		
		AdminResponseDto result = adminUserService.userDetailList(userNo);
		
		model.addAttribute("result", result);
		
		return "admin/user-detail";
	}
}

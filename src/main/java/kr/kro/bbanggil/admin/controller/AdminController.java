package kr.kro.bbanggil.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.kro.bbanggil.admin.dto.AdminDto;
import kr.kro.bbanggil.admin.service.AdminService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@GetMapping("/form")
	public String adminForm(AdminDto adminDto, Model model) {

		List<AdminDto> result = adminService.list(adminDto);
		
		model.addAttribute("results", result);
		
		return "admin/admin-page";
	}

	@GetMapping("/bakery/detail")
	public String bakeryDetailForm() {
		return "admin/bakery-detail";
	}

	@GetMapping("/user/detail")
	public String userDetailForm() {
		return "admin/user-detail";
	}

}

package kr.kro.bbanggil.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

		@GetMapping("/form")
		public String adminForm() {
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

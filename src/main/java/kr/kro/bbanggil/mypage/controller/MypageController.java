package kr.kro.bbanggil.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MypageController {
	
	@GetMapping("/mypage")
	public String myPage(Model model) {
		model.addAttribute("goOwnerPage",true);
		return "user/mypage";
		
	}
	
	@GetMapping("/edit")
	public String edit() {
		
		return "user/edit";
		
	}
	
	@GetMapping("owner/mypage")
	public String ownerMypage(Model model) {
		model.addAttribute("goMyPage",true);
		return "owner/owner-mypage";
	}
	
}

package kr.kro.bbanggil.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MypageController {
	
	@GetMapping("/mypage")
	public String myPage() {
		
		return "user/mypage";
		
	}
	
	@GetMapping("/edit")
	public String edit() {
		
		return "user/edit";
		
	}
	
}

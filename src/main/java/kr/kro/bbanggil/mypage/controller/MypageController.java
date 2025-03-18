package kr.kro.bbanggil.mypage.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.kro.bbanggil.mypage.service.MypageService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/mypage")
@AllArgsConstructor
public class MypageController {
	private MypageService service;
	
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
		int no = 3;
		Map<String,Object> result = service.getOwnerMypage(no);
		model.addAttribute("goMyPage",true);
		return "owner/owner-mypage";
	}
	
}

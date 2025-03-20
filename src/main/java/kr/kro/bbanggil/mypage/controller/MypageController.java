package kr.kro.bbanggil.mypage.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.kro.bbanggil.mypage.model.dto.response.MypagePageInfoDto;
import kr.kro.bbanggil.mypage.service.MypageServiceImpl;
import kr.kro.bbanggil.mypage.util.MypagePagination;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/mypage")
@AllArgsConstructor
public class MypageController {
	
	private final MypageServiceImpl mypageService;
	private final MypagePagination mypagePagination;
	
	@GetMapping("/mypage/form") 
	public String myPageForm(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
							  Model model) {
		
		int pageLimit = 10;
		int boardLimit = 10;

		Map<String, Object> result = mypageService.getAllPosts(mypagePagination, 
											  					currentPage,
											  					postCount,
											  					pageLimit,
											  					boardLimit);
		
		MypagePageInfoDto npiResult = (mypagePagination) result.get("pi");

            model.addAttribute("pi", npiResult);
             
		return "user/mypage";
		
	}
	
	
	@GetMapping("/edit")
	public String edit() {
		
		return "user/edit";
		
	}
	
}

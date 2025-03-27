package kr.kro.bbanggil.member.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.kro.bbanggil.member.model.dto.response.OwnerInfoResponseDTO;
import kr.kro.bbanggil.member.model.dto.response.OwnerMypageResponseDTO;
import kr.kro.bbanggil.member.service.MypageServiceImpl;
import kr.kro.bbanggil.member.util.MypagePagination;
import kr.kro.bbanggil.mypage.model.dto.request.PasswordRequestDto;
import kr.kro.bbanggil.mypage.model.dto.response.MypageListResponseDto;
import kr.kro.bbanggil.mypage.model.dto.response.MypagePageInfoDto;
import kr.kro.bbanggil.mypage.model.dto.response.MypageUserResponseDto;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class MypageController {
	
	private final MypageServiceImpl mypageService;
	private final MypagePagination mypagePagination;
	

	@GetMapping("/mypage") 
	public String myPage(@RequestParam(value="currentPage", defaultValue="1") int currentPage,
						 @SessionAttribute(value="userNum",required=false)Integer userNo,
				//		 @RequestParam(value="userNo",defaultValue="1") int userNo,
							@SessionAttribute(value="userName",required=false)String userName,
							MypageListResponseDto mypageDto,
							 HttpSession session,
							 Model model) {

		
		int postCount = mypageService.getTotalCount(userNo);
		int pageLimit = 10;
		int boardLimit = 10;
		
		
		Map<String, Object> result = mypageService.getMyList(mypagePagination, 
															currentPage,
															currentPage,
															pageLimit,
															boardLimit,
															userNo);
		
		MypageListResponseDto userInfo = mypageService.getMyInfo(userNo);
		
		List<MypageListResponseDto> getBuyHistory = (List<MypageListResponseDto>) result.get("getBuyHistory");
		
		model.addAttribute("getBuyHistory",getBuyHistory);
		 model.addAttribute("userName",userName);
		 model.addAttribute("userInfo",userInfo);
		MypagePageInfoDto piResult = (MypagePageInfoDto) result.get("pi");
		
		if(session.getAttribute("role").equals("owner"))
			model.addAttribute("goOwnerPage",true);
	    	else
	    	model.addAttribute("goOwnerPage",false);
            model.addAttribute("pi", piResult);
            
           
            
             
		return "user/mypage";
		
	}
	
	
	@GetMapping("/edit/form")
	public String editForm(  @SessionAttribute(value="userNum",required=false)Integer userNo,
			 		//		@RequestParam(value="userNo",defaultValue="1") int userNo,
			 				Model model) {
		
		MypageListResponseDto userInfo = mypageService.getMyInfo(userNo);
		model.addAttribute("userInfo",userInfo);
			
		return "user/edit";
		
	}
	
	@PostMapping("/edit")
	public String edit(MypageUserResponseDto mypageDto,
						@SessionAttribute(value="userNum",required=false)Integer userNo
					   ) {
		System.out.println(mypageDto.getUserName());
		 mypageService.updateUser(mypageDto,userNo);
		return "redirect:/register/mypage";
	}
	
	@PostMapping("/updatePassword")
	public String updatePassword(@ModelAttribute("passwordDto") @Valid PasswordRequestDto passwordDto,
								@SessionAttribute(value="userNum",required=false)Integer userNo) {

		mypageService.updatePassword(userNo,passwordDto);
	
		return "redirect:/register/mypage";
	}
	
	@GetMapping("owner/mypage")
	public String ownerMypage(@SessionAttribute("userNum") int userNum,
							  Model model) {
		List<OwnerMypageResponseDTO> result = mypageService.ownerMypage(userNum);
		OwnerInfoResponseDTO info = mypageService.ownerInfo(userNum);
		model.addAttribute("info",info);
		model.addAttribute("bakeries",result);
		model.addAttribute("goMyPage",true);
		
		return "owner/owner-mypage";
	}
	
}

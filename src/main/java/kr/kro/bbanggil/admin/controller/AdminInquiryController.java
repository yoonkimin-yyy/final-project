package kr.kro.bbanggil.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;
import kr.kro.bbanggil.admin.service.AdminInquiryService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/inquiry")
@RequiredArgsConstructor
public class AdminInquiryController {
	private final AdminInquiryService adminInquiryService;
	
	@GetMapping("/list")
	public String inquiryList(Model model) {
		List<InquiryResponseDto> inquiries = adminInquiryService.getInquiryList();
		
		 model.addAttribute("inquiries", inquiries);
		 
	      return "admin/inquiry-list";
	}
	
	@PostMapping("/answer")
	public String saveAnswer(@ModelAttribute InquiryReplyRequestDto inquiryReplyDto,
						HttpSession session){
		
		
		  Integer adminNo = (Integer) session.getAttribute("userNum");
		  inquiryReplyDto.setAdminNo(adminNo);
		
		  adminInquiryService.saveAnswer(inquiryReplyDto);
		  
		  int inquiryNo = inquiryReplyDto.getInquiryNo();

		  
		  InquiryResponseDto answer = adminInquiryService.getInquiryByNo(inquiryNo);
			
			return "redirect:/admin/inquiry/list"; // 저장 후 리스트로 리다이렉트
	}
}

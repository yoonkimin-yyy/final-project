package kr.kro.bbanggil.admin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;
import kr.kro.bbanggil.admin.service.AdminService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

	private final AdminService adminService;
	
	@GetMapping("/login")
	public String adminLoginForm() {
		return "admin/admin-login";
	}
	
	@GetMapping("/form")
	public String adminForm(Model model) {
		Map<String,Object> topContent = adminService.trafficMonitoring();
		Map<String,Object> bottomContent = adminService.bottomContent();
		
		List<AdminResponseDto> sublist = adminService.subList();

		List<AdminResponseDto> bakeryList = adminService.bakeryList();
		List<AdminResponseDto> userList = adminService.userList();
		
		model.addAttribute("today", topContent.get("today"));
		model.addAttribute("user", topContent.get("user"));
		model.addAttribute("order", topContent.get("order"));
		model.addAttribute("sublists", sublist);
		model.addAttribute("bakeryLists", bakeryList);
		model.addAttribute("userLists", userList);
		model.addAttribute("newOrder", bottomContent.get("new"));
		model.addAttribute("inquiries", bottomContent.get("inquiry"));
		
		return "admin/admin-page";
	}

	@GetMapping("/bakery/detail")
	public String bakeryDetailForm(@RequestParam("listNum") int listNum,
			   					   Model model) {
		
		AdminResponseDto result = adminService.bakeryDetailList(listNum);
		
		model.addAttribute("result", result);

		return "admin/bakery-detail";
	}

	@GetMapping("/user/detail")
	public String userDetailForm(@RequestParam("listNum") int listNum,
								 Model model) {
		
		AdminResponseDto result = adminService.userDetailList(listNum);
		
		model.addAttribute("result", result);
		
		return "admin/user-detail";
	}

	@GetMapping("/bakery/accept")
	public String bakeryAcceptForm(@RequestParam("listNum") int listNum, Model model) {

		AdminResponseDto result = adminService.acceptList(listNum);

		for (int i = 0; i < result.getBakeryImgPath().size(); i++) {
			System.out.println(result.getBakeryImgPath().get(i).getResourcesPath());
			System.out.println(result.getBakeryImgPath().get(i).getChangeName());
		}

		
		model.addAttribute("result", result);
		model.addAttribute("listNum", listNum);

		return "admin/bakery-accept";
	}

	@PostMapping("/bakery/update")
	@ResponseBody
	public String bakeryUpdateForm(@RequestParam("action") String action, @RequestParam("listNum") int listNum,
			@RequestParam("rejectReason") String rejectReason) {

		adminService.update(action, listNum, rejectReason);

		String message = ("승인".equals(action) ? "승인" : "거절") + " 완료되었습니다.";

		return "<script>alert('" + message + "'); window.opener.location.reload(); window.close();</script>";
	}

	@GetMapping("/inquiry-write")
	public String inquiryWriteForm() {
		return "admin/admin-inquiry";
	}

	@GetMapping("/inquiry-list")
	public String inquiryListForm() {
		return "admin/admin-inquiry-list";
	}

	/*
	 * 문의 등록 처리
	 */
	@PostMapping("/submit")
	public String submitInquiry(HttpSession session, @ModelAttribute InquiryRequestDto inquiryRequestDto, Model model) {

		// 1.문의 저장
		Integer userNo = (Integer) session.getAttribute("userNum");

		inquiryRequestDto.setUserNo(userNo);

		adminService.saveInquiry(inquiryRequestDto);

		return "redirect:/";
	}

	
	@GetMapping("/inquiry/list")
	public String inquiryList(Model model) {
		List<InquiryResponseDto> inquiries = adminService.getInquiryList();
		
		 model.addAttribute("inquiries", inquiries);
		 
	      return "admin/inquiry-list";
	}
	@PostMapping("/inquiry/answer")
	public String saveAnswer(@ModelAttribute InquiryReplyRequestDto inquiryReplyDto,
						HttpSession session){
		
		
		  Integer adminNo = (Integer) session.getAttribute("userNum");
		  inquiryReplyDto.setAdminNo(adminNo);
		
			adminService.saveAnswer(inquiryReplyDto);
			
			return "redirect:/admin/inquiry/list"; // 저장 후 리스트로 리다이렉트
	}
	

	
	
	
	
	
	
}

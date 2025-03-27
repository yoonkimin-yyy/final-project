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

import jakarta.servlet.http.HttpSession;
import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;
import kr.kro.bbanggil.admin.dto.response.MenuResponseDto;
import kr.kro.bbanggil.admin.service.AdminService;
import kr.kro.bbanggil.bakery.dto.response.PageResponseDto;
import kr.kro.bbanggil.common.util.PaginationUtil;
import kr.kro.bbanggil.order.dto.response.OrderResponseDto;
import kr.kro.bbanggil.order.service.OrderServiceImpl;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

	private final AdminService adminService;
	private final OrderServiceImpl orderService;
	
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
	public String bakeryDetailForm(@RequestParam("bakeryNo") int bakeryNo,
			   					   @RequestParam("userNo") int userNo,
			   					   Model model) {

		AdminResponseDto result = adminService.bakeryDetailList(bakeryNo, userNo);
		
		model.addAttribute("result", result);

		return "admin/bakery-detail";
	}

	@GetMapping("/user/detail")
	public String userDetailForm(@RequestParam("userNo") int userNo,
								 Model model) {
		
		AdminResponseDto result = adminService.userDetailList(userNo);
		
		model.addAttribute("result", result);
		
		return "admin/user-detail";
	}

	@GetMapping("/bakery/accept")
	public String bakeryAcceptForm(@RequestParam("listNum") int listNum,
								   @RequestParam("bakeryNo") int bakeryNo,
								   @RequestParam("userNo") int userNo,
								   Model model) {
		
		AdminResponseDto result = adminService.acceptList(bakeryNo, userNo);
		List<MenuResponseDto> menuList = adminService.menuList(bakeryNo);
		
		model.addAttribute("result", result);
		model.addAttribute("listNum", listNum);
		model.addAttribute("menuList", menuList);

		return "admin/bakery-accept";
	}

	@PostMapping("/bakery/update")
	public String bakeryUpdateForm(@RequestParam("action") String action,
								   @RequestParam("bakeryNo") int bakeryNo,
								   @RequestParam("rejectReason") String rejectReason) {
		
		adminService.update(action, bakeryNo, rejectReason);
		
		return "redirect:/admin/form";
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
	
	@GetMapping("/order/list")
	public String getOrderList(@RequestParam(value="currentPage",defaultValue="1")int currentPage,Model model) {
		
		
		int listCount = orderService.getOrderCount(); // 전체 주문수
		int pageLimit = 5;
		int boardLimit = 10;
		
		PageResponseDto pi = PaginationUtil.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		
		List<OrderResponseDto> orderList = orderService.getPagedOrders(pi);
		model.addAttribute("orderList", orderList);
		model.addAttribute("pi", pi);
		
		
		return "admin/admin-order-list";
	}
	
}

package kr.kro.bbanggil.admin.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;
import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.request.ReportRequestDTO;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;
import kr.kro.bbanggil.admin.dto.response.MenuResponseDto;
import kr.kro.bbanggil.admin.service.AdminService;
import kr.kro.bbanggil.admin.service.AdminServiceImpl;
import kr.kro.bbanggil.common.dto.response.SubscriptionResponseDto;
import kr.kro.bbanggil.common.mapper.EmailMapper;
import kr.kro.bbanggil.common.service.EmailServiceImpl;
import kr.kro.bbanggil.common.util.PaginationUtil;
import kr.kro.bbanggil.owner.order.dto.response.OrderResponseDto;
import kr.kro.bbanggil.owner.order.service.OrderServiceImpl;
import kr.kro.bbanggil.user.bakery.dto.response.PageResponseDto;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

	private final AdminService adminService;
	private final OrderServiceImpl orderService;
	private final EmailServiceImpl emailService;
	private final EmailMapper emailMapper;
	private final Logger logger = LogManager.getLogger(AdminServiceImpl.class);
	
	@GetMapping("/login")
	public String adminLoginForm() {
		return "admin/admin-login";
	}
	
	@GetMapping("/form")
	public String adminForm(Model model) {
		Map<String,Object> topContent = adminService.trafficMonitoring();
		Map<String,Object> bottomContent = adminService.bottomContent();
		List<AdminResponseDto> reportList = adminService.reportList();
		
		List<AdminResponseDto> sublist = adminService.subList();

		List<AdminResponseDto> bakeryList = adminService.bakeryList();
		List<AdminResponseDto> userList = adminService.userList();
		
		
		model.addAttribute("today", topContent.get("today"));
		model.addAttribute("user", topContent.get("user"));
		model.addAttribute("order", topContent.get("order"));
		model.addAttribute("sublists", sublist);
		model.addAttribute("bakeryLists", bakeryList);
		model.addAttribute("userLists", userList);
		model.addAttribute("reportLists", reportList);
		
		model.addAttribute("newOrder", bottomContent.get("new"));
		model.addAttribute("inquiries", bottomContent.get("inquiry"));
		
		return "admin/admin-page";
	}

	@GetMapping("/report/form")
	public String reportForm(@RequestParam("reportNo")int reportNo,
							 Model model) {
		AdminResponseDto result = adminService.reportDetail(reportNo);
		model.addAttribute("result",result);
		model.addAttribute("reportNo",reportNo);
		return "admin/report-reply";
	}
	@PostMapping("report")
	public String report(ReportRequestDTO reportDTO,
						 @SessionAttribute("userId")String userId,
						 @RequestParam("reportNo")int reportNo) {
		adminService.insertReport(reportDTO,userId,reportNo);
		logger.info("report: '{}'", userId);
		return "redirect:/admin/form";
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
		
		logger.info("/bakery/accept: '{}'", bakeryNo);

		return "admin/bakery-accept";
	}

	@PostMapping("/bakery/update")
	public String bakeryUpdateForm(@RequestParam("action") String action,
								   @RequestParam("bakeryNo") int bakeryNo,
								   @RequestParam("rejectReason") String rejectReason) {
		
		adminService.update(action, bakeryNo, rejectReason);
		
		logger.info("/bakery/update: '{}'", bakeryNo);
		
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
		  
		  int inquiryNo = inquiryReplyDto.getInquiryNo();

		  
		  InquiryResponseDto answer = adminService.getInquiryByNo(inquiryNo);
		  
		  logger.info("/inquiry/answer: '{}'", inquiryNo);
			
		  return "redirect:/admin/inquiry/list"; // 저장 후 리스트로 리다이렉트
	}
	
	@GetMapping("/order/list")
	public String getOrderList(@RequestParam(value="currentPage",defaultValue="1")int currentPage,
			@RequestParam(value = "keyword", required = false) String keyword,Model model) {
		
		
		int listCount = orderService.getOrderCount(keyword); // 전체 주문수
		int pageLimit = 5;
		int boardLimit = 10;
		
		PageResponseDto pi = PaginationUtil.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		
		List<OrderResponseDto> orderList = orderService.getPagedOrders(pi,keyword);
		model.addAttribute("orderList", orderList);
		model.addAttribute("pi", pi);
		model.addAttribute("keyword", keyword);
		
		return "admin/admin-order-list";
	}
	
	
	@GetMapping("/newsLetter")
	public String goNewsLetter(Model model) {
		
		/*
		 * 총 구독자 확인
		 */
		List<SubscriptionResponseDto> subscribeList = emailService.getAllSubscribers(); 
		model.addAttribute("subscribers", subscribeList);
		
		
		/*
		 * 다음 월요일 오전 9시 계산
		 */
		LocalDate today = LocalDate.now();
	    int daysUntilNextMonday = (DayOfWeek.MONDAY.getValue() - today.getDayOfWeek().getValue() + 7) % 7;
	    if (daysUntilNextMonday == 0) daysUntilNextMonday = 7; // 오늘이 월요일이면 다음 주

	    LocalDate nextMonday = today.plusDays(daysUntilNextMonday);
	    LocalDateTime nextNewsletterTime = LocalDateTime.of(nextMonday, LocalTime.of(9, 0));
	    String formattedDate = nextNewsletterTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		
		model.addAttribute("nextSchedule", formattedDate);
		
		/*
		 * 성공률 계산
		 */
		
		int successRate = emailService.getSendSuccessRate();
	    model.addAttribute("sendSuccessRate", successRate);
		
	    System.out.println(successRate);
		
		return "admin/admin-news-letter";
	}
	/*
	 * 구독 해지
	 */
	@PostMapping("/unsubscribe")
	public String unsubscribe(@RequestParam("email") String email,Model model,RedirectAttributes redirectAttributes) {
		
		emailMapper.unsubscribeEmail(email);
		
		redirectAttributes.addFlashAttribute("message", "구독이 정상적으로 해지되었습니다.");
		
		logger.info("/unsubscribe: '{}'", email);
		
		return "redirect:/admin/newsLetter";
	}
	
}

package kr.kro.bbanggil.admin.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.NewsletterResponseDto;
import kr.kro.bbanggil.admin.service.AdminMainService;
import kr.kro.bbanggil.common.dto.response.SubscriptionResponseDto;
import kr.kro.bbanggil.common.mapper.EmailMapper;
import kr.kro.bbanggil.common.service.EmailServiceImpl;
import kr.kro.bbanggil.newsletter.service.NewsletterServiceImpl;
import kr.kro.bbanggil.owner.order.service.OrderServiceImpl;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminMainController {

	private final AdminMainService adminMainService;
	private final OrderServiceImpl orderService;
	private final EmailServiceImpl emailService;
	private final EmailMapper emailMapper;
	private final NewsletterServiceImpl newsletterService;
	
	@GetMapping("/login")
	public String adminLoginForm() {
		return "admin/admin-login";
	}
	
	@GetMapping("/form")
	public String adminForm(Model model) {
		Map<String,Object> topContent = adminMainService.trafficMonitoring();
		Map<String,Object> bottomContent = adminMainService.bottomContent();
		List<AdminResponseDto> reportList = adminMainService.reportList();
		
		List<AdminResponseDto> sublist = adminMainService.subList();

		List<AdminResponseDto> bakeryList = adminMainService.bakeryList();
		List<AdminResponseDto> userList = adminMainService.userList();
		
		
		model.addAttribute("today", topContent.get("today"));
		model.addAttribute("user", topContent.get("user"));
		model.addAttribute("order", topContent.get("order"));
		model.addAttribute("sublists", sublist);
		model.addAttribute("bakeryLists", bakeryList);
		model.addAttribute("userLists", userList);
		model.addAttribute("reportLists", reportList);
		
		model.addAttribute("newOrder", bottomContent.get("new"));
		model.addAttribute("inquiries", bottomContent.get("inquiry"));
		model.addAttribute("report",bottomContent.get("report"));
		
		return "admin/admin-page";
	}

	@GetMapping("/newsLetter")
	public String goNewsLetter(Model model) {

	    /*
	     * /  총 구독자 리스트 조회
	     */
	    List<SubscriptionResponseDto> subscribeList = emailService.getAllSubscribers(); 
	    model.addAttribute("subscribers", subscribeList);

	    /*
	     * /  다음 뉴스레터 발송 예정 시간 계산 (→ 서비스에서 가져옴)
	     */
	    String formattedDate = newsletterService.calculateNextNewsletterSchedule();
	    model.addAttribute("nextSchedule", formattedDate);

	    /*
	     * / 뉴스레터 발송 성공률 조회
	     */
	    int successRate = emailService.getSendSuccessRate();
	    model.addAttribute("sendSuccessRate", successRate);

	    /*
	     * /  최근 뉴스레터 1건 조회
	     */
	    NewsletterResponseDto recentNewsletter = newsletterService.getLatestNewsletter();
	    model.addAttribute("newsletter", recentNewsletter);

	    return "admin/admin-news-letter";
	}

	/*
	 * 구독 해지
	 */
	@PostMapping("/unsubscribe")
	public String unsubscribe(@RequestParam("email") String email,Model model,RedirectAttributes redirectAttributes) {
		
		emailMapper.unsubscribeEmail(email);
		
		redirectAttributes.addFlashAttribute("message", "구독이 정상적으로 해지되었습니다.");
		
		return "redirect:/admin/newsLetter";
	}
	
	
	
	
	
	
	
	
}

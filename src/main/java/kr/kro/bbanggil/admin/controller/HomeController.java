package kr.kro.bbanggil.admin.controller;



import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
import kr.kro.bbanggil.admin.service.AdminServiceImpl;
import kr.kro.bbanggil.common.dto.request.SubscriptionRequsetDto;
import kr.kro.bbanggil.common.scheduler.NewsletterScheduler;
import kr.kro.bbanggil.common.service.EmailServiceImpl;
import kr.kro.bbanggil.user.bakery.dto.BakeryDto;
import kr.kro.bbanggil.user.bakery.service.BakeryServiceImpl;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
	
	
	private final EmailServiceImpl emailService;
	private final NewsletterScheduler newsletterScheduler;
	private final BakeryServiceImpl bakeryService;
	private final AdminServiceImpl adminService;
	private final Logger logger = LogManager.getLogger(AdminServiceImpl.class);
	
	/*
	 * 이메일로 구독 알림 보내는 기능
	 */
	@PostMapping("/subscribe")
	public ResponseEntity<String> subscribe(@Valid @RequestBody SubscriptionRequsetDto request, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
	    }

	    boolean isSuccess = emailService.sendSubscriptionEmail(request.getEmail());
	    

	    if (isSuccess) {
	        return ResponseEntity.ok("구독 완료! 확인 이메일을 발송했습니다.");
	    } else {
	        return ResponseEntity.status(500).body("구독 이메일 발송 실패. 다시 시도해주세요.");
	    }
	}
	
	/*
	 * 이메일로 뉴스레터 발송
	 */
	@PostMapping("/send")
	public ResponseEntity<String> sendNewsletterNow(){
		
		newsletterScheduler.sendWeeklyNewsletter();
		return ResponseEntity.ok("뉴스레터 즉시 발송 완료");
		
	}
	@GetMapping("/letter")
	public String sendletter() {
		return "admin/newsLetter";
	}
	
	@GetMapping("")
	public String homePage(@RequestParam(value = "bakeryNo", required = false, defaultValue = "0") double bakeryNo,Model model) {
		/*
		 * 인기 빵집 10개 보여지는 기능
		 */
		List<BakeryDto> popularBakeries = bakeryService.getPopularBakeries();
		model.addAttribute("popularBakeries", popularBakeries);
		
		/*
		 * 최근 오픈빵집 10개 보여지는 기능
		 */
		List<BakeryDto> recentBakeries = bakeryService.getRecentBakeries(bakeryNo);
		model.addAttribute("recentBakeries", recentBakeries);
		
		
		/*
		 * 주문량 상위5개를 받아오는 기능
		 */
		List<BakeryDto> topBakeries = bakeryService.getTopFiveOrders();
		model.addAttribute("topBakeries", topBakeries);
		
		
		/*
		 * 주문량 상위5개를 받아서 카테고리 별로 가게 개수 보여지는기능
		 */
		List<BakeryDto> categoryBakeries = bakeryService.getCategoryBakeries(topBakeries);
		model.addAttribute("categoryBakeries", categoryBakeries);
		
		
		return "common/home";
	}
	
	@GetMapping("by-region")
	@ResponseBody
	public List<BakeryDto> getBakeriesByRegion(@RequestParam("region") String region){
		
		logger.info("by-region: '{}'", region);
		
		return bakeryService.getBakeriesByRegion(region);
		
	}
	
	
	/*
	 * 문의 등록 처리
	 */
	
	@PostMapping("/submit")
	public String submitInquiry(HttpSession session, @ModelAttribute InquiryRequestDto inquiryRequestDto, Model model) {

		// 1.문의 저장
		Integer userNo = (Integer) session.getAttribute("userNum");

		 if (userNo != null) {
		        // 로그인 상태일 경우
		        inquiryRequestDto.setUserNo(userNo);
		    } else {
		        // 비회원인 경우
		        inquiryRequestDto.setUserNo(null);
		    }

		adminService.saveInquiry(inquiryRequestDto);
		
		logger.info("/submit: '{}'", userNo);

		return "redirect:/";
	}
	
	@GetMapping("/inquiry-write")
	public String showInquiryForm() {
	    return "admin/admin-inquiry"; // 뷰 파일 이름이 admin-inquiry.html이면 이렇게!
	}
	
	
	
}
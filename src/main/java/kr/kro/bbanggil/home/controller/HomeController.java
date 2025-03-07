package kr.kro.bbanggil.home.controller;



import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.kro.bbanggil.bakery.dto.BakeryDto;
import kr.kro.bbanggil.bakery.response.dto.BakeryResponseReviewDto;
import kr.kro.bbanggil.bakery.service.BakeryServiceImpl;
import kr.kro.bbanggil.common.util.EmailServiceImpl;
import kr.kro.bbanggil.common.util.NewsletterScheduler;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/bbanggil")
@RequiredArgsConstructor
public class HomeController {
	
	
	private final EmailServiceImpl emailService;
	private final NewsletterScheduler newsletterScheduler;
	private final BakeryServiceImpl bakeryService;
	
	/*
	 * 이메일로 구독 알림 보내는 기능
	 */
	@PostMapping("/subscribe")
	public ResponseEntity<String> subscribe(@RequestParam("email") String email) {
  

	    if (email == null || email.isEmpty()) {
	        return ResponseEntity.badRequest().body(" 이메일을 입력해주세요.");
	    }

	    try {
	        emailService.sendSubscriptionEmail(email);
	        return ResponseEntity.ok(" 구독 완료! 확인 이메일을 발송했습니다.");
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(" 구독 이메일 발송 실패: " + e.getMessage());
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
	
	@GetMapping("/bakeries")
	public ResponseEntity<List<BakeryDto>> getBakeriesByRegion(@RequestParam("region") String region){

		  List<BakeryDto> bakeries = bakeryService.getBakeriesByRegion(region);
		    
		  return ResponseEntity.ok(bakeries);
		    
	}
	
	@GetMapping("/home")
	public String homePage(Model model) {
		/*
		 * 인기 빵집 10개 보여지는 기능
		 */
		List<BakeryDto> popularBakeries = bakeryService.getPopularBakeries();
		model.addAttribute("popularBakeries", popularBakeries);
		
		/*
		 * 최근 오픈빵집 10개 보여지는 기능
		 */
		List<BakeryDto> recentBakeries = bakeryService.getRecentBakeries();
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
	
	
	
	
	
	
	
	
	
	
}
package kr.kro.bbanggil.home.controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.kro.bbanggil.bakery.dto.BakeryDto;
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
	

	@GetMapping("/home")
	public String home() {
		
		return "/common/home";
	}
	
	@PostMapping("/subscribe")
	public ResponseEntity<String> subscribe(@RequestParam("email") String email) {

	    System.out.println(" 받은 이메일: " + email);  

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
	@GetMapping("/popular")
	public List<BakeryDto> getPopularBakeries(){
		return bakeryService.getPopularBakeries();
	}
	
	
	
	
	
	
	
	
	
	
	
}
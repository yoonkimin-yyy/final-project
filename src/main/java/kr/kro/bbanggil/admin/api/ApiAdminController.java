package kr.kro.bbanggil.admin.api;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.kro.bbanggil.admin.dto.request.AdminEmailRequestDto;
import kr.kro.bbanggil.admin.dto.response.MonthlyOrderResponseDTO;
import kr.kro.bbanggil.admin.service.AdminMainService;
import kr.kro.bbanggil.admin.service.AdminMainServiceImpl;
import kr.kro.bbanggil.common.scheduler.NewsletterScheduler;
import kr.kro.bbanggil.common.service.EmailServiceImpl;
import kr.kro.bbanggil.user.bakery.service.BakeryServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ApiAdminController {
	
	private final AdminMainService adminMainService;
	private final Logger logger = LogManager.getLogger(ApiAdminController.class);
	
	@PostMapping("/sendEmail")
	public String sendEmail(@RequestBody AdminEmailRequestDto adminReqeustDto) {
		
		adminMainService.sendEmail(adminReqeustDto);
		
		logger.info("/sendEmail: '{}'", adminReqeustDto.getAddress());
		
		return "ok";
	}
	
	@GetMapping("/monthly-count")
	public ResponseEntity<List<MonthlyOrderResponseDTO>> getMonthlyOrderCount(){
		return ResponseEntity.ok(adminMainService.getMonthlyOrderCount());
	}
	
	@PostMapping("/addCategory")
	public String addCategory(@RequestParam("category") String newCategory) {
		
		adminMainService.addCategory(newCategory);
		
		logger.info("/addCategory: '{}'", newCategory);
		
		return "ok";
	}
	
	@PostMapping("/deleteCategory")
	public String deleteCategory(@RequestBody Map<String, List<String>> requestBody) {
		
		adminMainService.deleteCategory(requestBody);
				
		logger.info("/deleteCategory: '{}'", requestBody);
		
	    return "ok";
    }
	
}

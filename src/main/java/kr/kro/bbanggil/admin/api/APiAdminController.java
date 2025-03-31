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
import kr.kro.bbanggil.admin.service.AdminService;
import kr.kro.bbanggil.admin.service.AdminServiceImpl;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class APiAdminController {
	
	private final AdminService adminService;
	private final Logger logger = LogManager.getLogger(AdminServiceImpl.class);
	
	@PostMapping("/sendEmail")
	@ResponseBody
	public String sendEmail(@RequestBody AdminEmailRequestDto adminReqeustDto) {
		
		adminService.sendEmail(adminReqeustDto);
		
		logger.info("이메일 수신자 : ", adminReqeustDto.getAddress());
		
		return "ok";
	}
	
	@GetMapping("/monthly-count")
	public ResponseEntity<List<MonthlyOrderResponseDTO>> getMonthlyOrderCount(){
		return ResponseEntity.ok(adminService.getMonthlyOrderCount());
	}
	
	@PostMapping("/addCategory")
	public String addCategory(@RequestParam("category") String newCategory) {
		
		adminService.addCategory(newCategory);
		
		return "ok";
	}
	
	@PostMapping("/deleteCategory")
	public String deleteCategory(@RequestBody Map<String, List<String>> requestBody) {
		
		adminService.deleteCategory(requestBody);
		    
	    return "ok";
    }
	
}

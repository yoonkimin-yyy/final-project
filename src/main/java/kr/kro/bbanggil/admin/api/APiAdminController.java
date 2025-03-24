package kr.kro.bbanggil.admin.api;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.kro.bbanggil.admin.dto.request.AdminEmailRequestDto;
import kr.kro.bbanggil.admin.service.AdminService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class APiAdminController {
	
	private final AdminService adminService;
	
	@PostMapping("/sendEmail")
	@ResponseBody
	public String sendEmail(@RequestBody AdminEmailRequestDto adminReqeustDto) {
		
		adminService.sendEmail(adminReqeustDto);
		
		return "ok";
	}
	
}

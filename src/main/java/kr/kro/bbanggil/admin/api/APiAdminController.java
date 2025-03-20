package kr.kro.bbanggil.admin.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.kro.bbanggil.admin.dto.request.AdminRequestDto;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class APiAdminController {
	
	@PostMapping("/sendEmail")
	@ResponseBody
	public String sendEmail(@RequestBody AdminRequestDto adminReqeustDto) {
		
		System.out.println(adminReqeustDto.getAddress());
		System.out.println(adminReqeustDto.getAddress());
		System.out.println(adminReqeustDto.getAddress());
		
		return "ok";
	}
	
}

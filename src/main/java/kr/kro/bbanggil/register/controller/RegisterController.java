package kr.kro.bbanggil.register.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@GetMapping("/loginin")
	public String loginIn() {
		
		return "common/loginin";
		
	}
	
	@GetMapping("/checkbox")
	public String checkBox() {
		
		return "common/checkbox";
		
	}
	
	@GetMapping("/findidpw")
	public String findIdPw() {
		
		return "common/find-id-pw";
		
	}
	
	@GetMapping("/typeloginup")
	public String typeLoginup() {
		
		return "common/type-loginup";
		
	}
	
	@GetMapping("/loginup")
	public String loginUp() {
		
		return "user/loginup";
		
	}
	
	@GetMapping("/businessloginup")
	public String businessLoginup() {
		
		return "owner/business-loginup";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
		
}

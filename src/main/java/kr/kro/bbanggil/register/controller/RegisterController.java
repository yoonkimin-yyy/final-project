package kr.kro.bbanggil.register.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	// 체크박스 
	@GetMapping("/checkbox")
	public String checkBox() {
		
		return "common/checkbox";
		
	}
	
	// 일반사용자 회원가입
	@GetMapping("/loginup")
	public String loginUp() {
		
		return "user/loginup";
		
	}

	// 사업자 회원가입
	@GetMapping("/businessloginup")
	public String businessLoginup() {
		
		return "owner/business-loginup";
		
	}
	
	// 로그인
	@GetMapping("/loginin")
	public String loginIn() {
		
		return "common/loginin";
		
	}
	
	// 아이디/비밀번호 찾기
	@GetMapping("/findidpw")
	public String findIdPw() {
		
		return "common/find-id-pw";
		
	}
	
	// 회원가입 타입 (일반/사업자)
	@GetMapping("/typeloginup")
	public String typeLoginup() {
		
		return "common/type-loginup";
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
		
}

package kr.kro.bbanggil.register.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
    // 회원가입 타입 선택 페이지 (일반/사업자 선택)
    @GetMapping("/typeloginup")
    public String typeLoginup() {
        return "common/type-loginup";
    }
    
    // 체크박스 페이지 (일반/사업자 구분)
    @GetMapping("/checkbox")
    public String checkBox(@RequestParam(value = "type", required = false, defaultValue = "user") String type, Model model) {
        model.addAttribute("userType", type); 
        return "common/checkbox";
    }

    // 회원가입 페이지로 이동 (일반/사업자 구분)
    @GetMapping("/next")
    public String nextStep(@RequestParam(value = "type", required = false, defaultValue = "user") String type) {
        if ("business".equals(type)) {
            return "redirect:/register/businessloginup"; // 사업자 회원가입 페이지로 이동
        } else {
            return "redirect:/register/loginup"; // 일반 회원가입 페이지로 이동
        }
    }

    // 일반 회원가입
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

}

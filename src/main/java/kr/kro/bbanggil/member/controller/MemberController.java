package kr.kro.bbanggil.member.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import kr.kro.bbanggil.member.model.dto.request.MemberRequestCheckBoxDto;
import kr.kro.bbanggil.member.model.dto.request.MemberRequestSignupDto;
import kr.kro.bbanggil.member.model.dto.response.OwnerMypageResponseDTO;
import kr.kro.bbanggil.member.service.MemberServiceImpl;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class MemberController {
    private final Logger logger = LogManager.getLogger(MemberController.class);
    private final MemberServiceImpl memberService;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 타입 선택 페이지 (일반/사업자 선택)
    @GetMapping("/typeloginup/form")
    public String typeLoginup() {
        return "/common/type-loginup";
    }

    // 체크박스 페이지 (일반/사업자 구분)
    @GetMapping("/checkbox")
    public String checkBox(@RequestParam(value = "type", required = false, defaultValue = "user") String type, Model model) {
        model.addAttribute("userType", type);
        model.addAttribute("checkBoxDto", new MemberRequestCheckBoxDto()); // 체크박스 DTO 추가
        return "/common/checkbox";
    }
    
    // 체크박스 
    @PostMapping("/checkbox")
    public String saveCheckBox(@ModelAttribute MemberRequestCheckBoxDto checkBoxDto,
                               @RequestParam(value = "type", required = false, defaultValue = "user") String type,
                               HttpSession session,
                               Model model) {

    	// 필수 항목 검증
        if (!"Y".equals(checkBoxDto.getTermsofuse()) || !"Y".equals(checkBoxDto.getInformation())) {
            model.addAttribute("error", "필수 항목을 체크해야 합니다.");
            model.addAttribute("userType", type);
            return "/common/checkbox";
        }

        // 세션에 체크박스 정보 저장
        session.setAttribute("checkBoxDto", checkBoxDto);

        // 회원가입 페이지로 이동 (일반/사업자)
        if ("business".equals(type)) {
            return "redirect:/register/businessloginup/form"; // 사업자 회원가입 경로
        } else {
            return "redirect:/register/loginup/form"; // 일반 회원가입 경로
        }
    }

    // 일반 회원가입 페이지
    @GetMapping("/loginup/form")
    public String loginUpForm(Model model, HttpSession session) {
        model.addAttribute("registerDto", new MemberRequestSignupDto());

        // 세션에서 체크박스 동의 정보 가져와서 회원가입 페이지에 전달
        MemberRequestCheckBoxDto checkBoxDto = (MemberRequestCheckBoxDto) session.getAttribute("checkBoxDto");
        if (checkBoxDto != null) {
            model.addAttribute("checkBoxDto", checkBoxDto);
        }


        return "/user/loginup";
    }

    // 일반 회원가입 처리
    @PostMapping("/loginup")
    public String loginup(@ModelAttribute MemberRequestSignupDto signupRequestDto, HttpSession session) {
        // 세션에서 체크박스 동의 정보 가져오기
        MemberRequestCheckBoxDto checkBoxRequestDto = (MemberRequestCheckBoxDto) session.getAttribute("checkBoxDto");

        if (checkBoxRequestDto != null) {
            memberService.loginup(signupRequestDto, checkBoxRequestDto);
            session.removeAttribute("checkBoxDto"); // 세션 삭제
        }


        return "redirect:/register/loginin/form";
    }

    // 아이디 중복 체크
    @GetMapping("/checkId/{userId}")
    @ResponseBody
    public boolean userIdCheck(@PathVariable("userId") String userId) {
        int checkResult = memberService.userIdCheck(userId);   
        return checkResult > 0; 
    }
    
    // 이메일 중복 체크
    @GetMapping("/checkEmail/{userEmail}")
    @ResponseBody
    public boolean userEmailCheck(@PathVariable("userEmail") String userEmail) {
    	int checkResult = memberService.userEmailCheck(userEmail);   
        return checkResult > 0;
    }

    // 사업자 회원가입 페이지
    @GetMapping("/businessloginup/form")
    public String businessLoginupForm(Model model, HttpSession session) {
        model.addAttribute("registerDto", new MemberRequestSignupDto());

        // 세션에서 체크박스 동의 정보 가져와서 회원가입 페이지에 전달
        MemberRequestCheckBoxDto checkBoxDto = (MemberRequestCheckBoxDto) session.getAttribute("checkBoxDto");
        if (checkBoxDto != null) {
            model.addAttribute("checkBoxDto", checkBoxDto);
        }

        return "/owner/business-loginup";
    }

    // 사업자 회원가입 처리
    @PostMapping("/businessloginup")
    public String businessloginup(@ModelAttribute MemberRequestSignupDto signupRequestDto, HttpSession session) {
        // 세션에서 체크박스 동의 정보 가져오기
        MemberRequestCheckBoxDto checkBoxRequestDto = (MemberRequestCheckBoxDto) session.getAttribute("checkBoxDto");

        if (checkBoxRequestDto != null) {
            memberService.businessloginup(signupRequestDto, checkBoxRequestDto);
            session.removeAttribute("checkBoxDto");
        }

        return "redirect:/register/loginin/form";
    }
    
    // 사업자번호 중복 체크 
    @GetMapping("/checkBusinessNo/{businessNo}")
    @ResponseBody
    public boolean checkBusinessNo(@PathVariable("businessNo") String businessNo) {
        int checkResult = memberService.businessNoCheck(businessNo);   
        return checkResult > 0;  // 중복이면 true, 없으면 false
    }

    // 로그인 페이지
    @GetMapping("/loginin/form")
    public String loginInForm(HttpSession session, Model model) {
    	 System.out.println("로그인 페이지 접근, 기존 status 값: " + session.getAttribute("status"));
    	    
    	 // 로그인 페이지 진입 시 에러 메시지 초기화
    	 session.removeAttribute("status");  
    	 return "/common/loginin";  
    }
    
    // 로그인 처리
    @PostMapping("/loginin")
    public String loginin(MemberRequestSignupDto memberRequestSignupDto, HttpSession session,
    					  RedirectAttributes redirectAttributes) {
        // 로그인 검증
    	MemberRequestSignupDto loginUser = memberService.loginIn(memberRequestSignupDto);
        System.out.println("로그인 결과: " + loginUser);
        

        if (loginUser != null) {
            // 로그인 성공 → 세션에 사용자 정보 저장
            session.setAttribute("userNum", loginUser.getUserNo());
            session.setAttribute("userId", loginUser.getUserId());
            session.setAttribute("userName", loginUser.getUserName());
            session.setAttribute("role", loginUser.getUserType());
            return "redirect:/";  
        } else {
            // 로그인 실패 메시지 전달
            redirectAttributes.addFlashAttribute("loginError", "아이디 또는 비밀번호가 틀렸습니다.");
            return "redirect:/register/loginin/form";
        }
    }

    // 아이디/비밀번호 찾기 페이지
    @GetMapping("/findidpw")
    public String findIdPw() {
        return "/common/find-id-pw";
    }
    
    
    @GetMapping("/logout")
	public String logout(HttpSession session) {
    	session.invalidate();
		return "redirect:/";
	}
 

	@GetMapping("/edit")
	public String edit() {

		return "user/edit";

	}

	@GetMapping("owner/mypage")
	public String ownerMypage(@SessionAttribute("userNum") int userNum,
							  Model model) {
		List<OwnerMypageResponseDTO> result =memberService.ownerMypage(userNum); 
		model.addAttribute("bakeries",result);
		model.addAttribute("goMyPage",true);
		return "owner/owner-mypage";
	}

}

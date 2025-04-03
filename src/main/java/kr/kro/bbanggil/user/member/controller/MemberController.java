package kr.kro.bbanggil.user.member.controller;

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.kro.bbanggil.common.util.LoginAttemptUtil;

import kr.kro.bbanggil.user.member.dto.request.MemberRequestCheckBoxDto;
import kr.kro.bbanggil.user.member.dto.request.MemberRequestSignupDto;
import kr.kro.bbanggil.user.member.service.FindIdPwServiceImpl;
import kr.kro.bbanggil.user.member.service.MemberServiceImpl;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {
    private final Logger logger = LogManager.getLogger(MemberController.class);
    private final MemberServiceImpl memberService;
    private final PasswordEncoder passwordEncoder;
    private final FindIdPwServiceImpl findIdPwService;
    private final LoginAttemptUtil loginAttemptUtil;

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
            return "redirect:/member/businessloginup/form"; // 사업자 회원가입 경로
        } else {
            return "redirect:/member/loginup/form"; // 일반 회원가입 경로
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


        return "redirect:/member/loginin/form";
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

        return "redirect:/member/loginin/form";
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

    	// 로그인 페이지 진입 시 에러 메시지 초기화
    	 session.removeAttribute("status");  
    	 return "/common/loginin";  
    }
    
    // 로그인 처리
    @PostMapping("/loginin")
    public String loginin(MemberRequestSignupDto memberRequestSignupDto, HttpSession session,
    					  RedirectAttributes redirectAttributes, HttpServletRequest request) {
    	
    	// IP 기준으로 계정 잠금 상태 확인
        if (loginAttemptUtil.isAccountLocked(request)) {
            redirectAttributes.addFlashAttribute("loginError", "5회 로그인 실패로 3분 동안 잠금 처리되었습니다.");
            return "redirect:/member/loginin/form";
        }
        
        // 로그인 검증
    	MemberRequestSignupDto loginUser = memberService.loginIn(memberRequestSignupDto);
    	
	    	if (loginUser != null) {
	    		 if(!loginUser.getUserType().equals("admin")) {
	    	        // 로그인 성공 → 세션에 사용자 정보 저장
	    	        session.setAttribute("userNum", loginUser.getUserNo());
	    	        session.setAttribute("userId", loginUser.getUserId());
	    	        session.setAttribute("userName", loginUser.getUserName());
	    	        session.setAttribute("role", loginUser.getUserType());
	    	        if(loginUser.isAlert())
	    	        session.setAttribute("alertMessage","리뷰 작성 시 주의해주세요 경고입니다.");
	    	        
	    	        return "redirect:/"; // 일반 사용자 메인
	    		 } else redirectAttributes.addFlashAttribute("adminBtn",true);
		    } else {
		        // 로그인 실패 → 에러 메시지 세팅 후 로그인 페이지로
		    }
		    loginAttemptUtil.incrementFailedAttempts(request);  // 실패 횟수 증가  // 실패 횟수 증가
		    redirectAttributes.addFlashAttribute("loginError", "아이디 또는 비밀번호가 틀렸습니다.");
		    return "redirect:/member/loginin/form";
    }
    
    
    @PostMapping("/logininAdmin")
    public String logininAdmin(MemberRequestSignupDto memberRequestSignupDto, HttpSession session,
    						   HttpServletRequest request,
    						   RedirectAttributes redirectAttributes) {
    	
    	// IP 기준으로 계정 잠금 상태 확인
        if (loginAttemptUtil.isAccountLocked(request)) {
            redirectAttributes.addFlashAttribute("loginError", "5회 로그인 실패로 3분 동안 잠금 처리되었습니다.");
            return "redirect:/admin/login";
        }

    	MemberRequestSignupDto loginUser = memberService.loginIn(memberRequestSignupDto);
    	
    	if (loginUser != null && loginUser.getUserType().equals("admin")) {
    		loginAttemptUtil.resetFailedAttempts(request);
    		
    		session.setAttribute("userNum", loginUser.getUserNo());
    		session.setAttribute("userId", loginUser.getUserId());
    		session.setAttribute("role", loginUser.getUserType());
    		
    		return "redirect:/admin/form";  
    	} 
    	
    	loginAttemptUtil.incrementFailedAttempts(request);  // 실패 횟수 증가
		redirectAttributes.addFlashAttribute("loginError", "아이디 또는 비밀번호가 틀렸습니다.");
		return "redirect:/admin/login";

    }

    // 아이디/비밀번호 찾기 페이지
    @GetMapping("/findidpw/form")
    public String findIdPwForm() {
        return "/common/find-id-pw";
    }
    
    /**
     * 아이디 찾기
     */
    @PostMapping("/find/id")
    public String findUserId(MemberRequestSignupDto memberRequestSignupDto, Model model) {
        try {
            String userId = findIdPwService.findUserIdByEmail(memberRequestSignupDto);
            
            if (userId == "등록된 정보가 아닙니다.") {
                // 이메일이 등록되지 않은 경우
                model.addAttribute("error", "등록된 정보가 아닙니다.");
                return "redirect:/member/find/form";  // 오류 페이지로 리다이렉트
            }
            
            // 이메일이 등록되어 있는 경우
            model.addAttribute("message", "아이디: " + userId);
            return "redirect:/member/loginin/form";  // 아이디를 찾았으면 로그인 페이지로 리다이렉트
        } catch (Exception e) {
            model.addAttribute("error", "아이디 찾기 중 오류 발생");
            e.printStackTrace();  // 오류 발생 시 로그로 확인
            return "redirect:/member/find/form";  // 오류가 발생하면 원래 폼 페이지로 리다이렉트
        }
    }



    /**
     * 비밀번호 재설정 (임시 비밀번호 발송)
     */
    @PostMapping("/find/pw")
    public String resetPassword(MemberRequestSignupDto memberRequestSignupDto, Model model) {
        try {
        	String message = findIdPwService.sendTemporaryPassword(memberRequestSignupDto);
        	if ("등록된 정보가 아닙니다.".equals(message)) {
                // 이메일이 등록되지 않은 경우
                model.addAttribute("error", "등록된 정보가 아닙니다.");
                return "redirect:/member/find/form";
            }
            model.addAttribute("message", message);
            return "redirect:/member/loginin/form";  // 오류 페이지로 리다이렉트
        } catch (Exception e) {
            model.addAttribute("error", "이메일 전송 실패");
            return "redirect:/member/find/form";
        }
    }
    
    @GetMapping("/logout")
	public String logout(HttpSession session) {
    	session.invalidate();
		return "redirect:/";
	}

}

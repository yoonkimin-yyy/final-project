package kr.kro.bbanggil.user.member.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.kro.bbanggil.global.exception.BbanggilException;
import kr.kro.bbanggil.user.member.dto.request.MemberRequestCheckBoxDto;
import kr.kro.bbanggil.user.member.dto.request.MemberRequestSignupDto;
import kr.kro.bbanggil.user.member.mapper.MemberMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberMapper registerMapper;
    private final PasswordEncoder passwordEncoder;
    
    // 체크박스 저장
    @Override
    public String checkbox(MemberRequestCheckBoxDto checkBoxRequestDto) {
        int result = registerMapper.insertCheckbox(checkBoxRequestDto);
        return (result > 0) ? "체크박스 정보가 저장되었습니다." : "체크박스 저장에 실패했습니다.";
    }

    // 일반유저 회원가입
    @Override
    public String loginup(MemberRequestSignupDto signupRequestDto, MemberRequestCheckBoxDto checkBoxRequestDto) {
        signupRequestDto.setUserPassword(passwordEncoder.encode(signupRequestDto.getUserPassword()));
        
        // 필수 동의 항목 검증 (DB에 'N'이 들어가지 않도록 방지)
        if (!"Y".equals(checkBoxRequestDto.getTermsofuse()) || !"Y".equals(checkBoxRequestDto.getInformation())) {
            return "회원가입에 실패했습니다. 필수 약관에 동의해야 합니다.";
        }
        

        int result = registerMapper.loginup(signupRequestDto);

        if (result > 0) {
            registerMapper.insertAddress(signupRequestDto);
            checkBoxRequestDto.setUserNo(signupRequestDto.getUserNo());
            registerMapper.insertCheckbox(checkBoxRequestDto);
            return "회원가입이 완료되었습니다.";
        } else {
            return "회원가입에 실패했습니다. 다시 시도해주세요.";
        }
    }

    // 사업자 회원가입
    @Override
    public String businessloginup(MemberRequestSignupDto signupRequestDto, MemberRequestCheckBoxDto checkBoxRequestDto) {
    	signupRequestDto.setUserPassword(passwordEncoder.encode(signupRequestDto.getUserPassword()));
        

     // 필수 동의 항목 검증 (DB에 'N'이 들어가지 않도록 방지)
        if (!"Y".equals(checkBoxRequestDto.getTermsofuse()) || !"Y".equals(checkBoxRequestDto.getInformation())) {
            return "회원가입에 실패했습니다. 필수 약관에 동의해야 합니다.";
        }

        int result = registerMapper.businessloginup(signupRequestDto);

        if (result > 0) {
            registerMapper.insertbusiness(signupRequestDto);
            checkBoxRequestDto.setUserNo(signupRequestDto.getUserNo());
            registerMapper.insertCheckbox(checkBoxRequestDto);
            return "회원가입이 완료되었습니다.";
        } else {
            return "회원가입에 실패했습니다. 다시 시도해주세요.";
        }
    }

    // 아이디 중복 체크
    @Override
    public int userIdCheck(String userId) {
        return registerMapper.userIdCheck(userId);
    
    }
    
    // 이메일 중복 체크
    @Override
    public int userEmailCheck(String userEmail) {
    	return registerMapper.userEmailCheck(userEmail);
    }
    
    // 사업자번호 중복 체크
    @Override
    public int businessNoCheck(String businessNo) {
        return registerMapper.businessNoCheck(businessNo); // 중복 개수 반환
    }
    
    // 로그인
    @Override
    public MemberRequestSignupDto loginIn(MemberRequestSignupDto memberRequestSignupDto) {
    	
    	MemberRequestSignupDto loginUser = registerMapper.loginIn(memberRequestSignupDto);
    	
         if (loginUser != null) {
             // 2. 비밀번호 검증 (암호화된 비밀번호와 입력된 비밀번호 비교)
             if (passwordEncoder.matches(memberRequestSignupDto.getUserPassword(),loginUser.getUserPassword())) {
            	 String isBanned = registerMapper.isBanned(memberRequestSignupDto);
	            	 if(isBanned.equals("N"))
	            	 return loginUser; // 로그인 성공            			 
	            	 else throw new BbanggilException("계정 정지 상태 입니다","common/error",HttpStatus.LOCKED);
             }
         }
         return null; // 로그인 실패
     }
}




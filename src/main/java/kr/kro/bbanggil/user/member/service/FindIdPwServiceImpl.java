package kr.kro.bbanggil.user.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.kro.bbanggil.common.service.EmailServiceImpl;
import kr.kro.bbanggil.global.config.FindIdConfig;
import kr.kro.bbanggil.user.member.dto.request.MemberRequestSignupDto;
import kr.kro.bbanggil.user.member.mapper.MemberMapper;
import kr.kro.bbanggil.user.member.util.PasswordUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindIdPwServiceImpl implements FindIdPwService {
	
	 private final BCryptPasswordEncoder passwordEncoder;
	 private final FindIdConfig findIdConfig;
	 private final EmailServiceImpl emailService;
	 private final MemberMapper memberMapper;

	 /**
	  * 이메일을 기반으로 아이디 찾기
	  */
	 @Override
	 public String findUserIdByEmail(MemberRequestSignupDto memberRequestSignupDto) {
	    
	     MemberRequestSignupDto member = memberMapper.findUserByEmail(memberRequestSignupDto);
	     
	     
	     // 아이디가 없으면 에러 메시지 반환
	     if (member.getUserId() == null) {
	         return "등록된 정보가 아닙니다.";
	     }

	     // 아이디 마스킹
	     String maskedUserId = findIdConfig.maskUserId(member.getUserId());

	     // 이메일 발송 내용
	     String subject = "[이빵이오] 아이디 찾기 안내";
	     String content = "<p>안녕하세요,<br> <strong>"+ member.getUserName() +"</strong>님의 아이디는 <strong>" 
	    		 			+ maskedUserId + "</strong> 입니다.</p>";
	     
	     try {
	    	 // 이메일 전송
	         emailService.sendEmail(memberRequestSignupDto.getUserEmail(), subject, content);
	     } catch (Exception e) {
	         e.printStackTrace();
	         return "이메일 전송에 실패했습니다.";
	     }

	        return "이메일이 전송되었습니다";
	    }

	 /**
	  * 임시 비밀번호 생성 및 이메일 발송
	  */
	 @Override
	 public String sendTemporaryPassword(MemberRequestSignupDto memberRequestSignupDto) {
	     MemberRequestSignupDto member = memberMapper.findUserPassword(memberRequestSignupDto);
	     if (member.getUserPassword() == null) {
	         return "등록된 정보가 아닙니다.";
	     }

	     String tempPassword = PasswordUtil.generateTempPassword();

	     // 비밀번호 암호화 후 저장
	     String encryptedPassword = passwordEncoder.encode(tempPassword);
	     member.setUserPassword(encryptedPassword);
	     int result = memberMapper.updatePassword(member);

	     if (result == 0) {
	         return "비밀번호 변경에 실패했습니다.";
	     }

	     // 이메일 전송
	     String subject = "[이빵이오] 임시 비밀번호 발급 안내";
	     String content = "<p>안녕하세요, <strong>" + member.getUserName() + "</strong>님</p><br>" +
	                      "<p>임시 비밀번호 : <strong>" + tempPassword + "</strong></p><br>" +
	                      "<p>로그인 후 마이페이지에서 비밀번호를 변경해주세요!</p>";

	     try {
	         emailService.sendEmail(memberRequestSignupDto.getUserEmail(), subject, content);
	     } catch (Exception e) {
	         e.printStackTrace();
	         return "오류가 발생했습니다.";
	     }

	     return "임시 비밀번호가 이메일로 발송되었습니다.";
	 }

}

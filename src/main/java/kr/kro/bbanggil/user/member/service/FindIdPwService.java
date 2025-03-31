package kr.kro.bbanggil.user.member.service;

import jakarta.mail.MessagingException;
import kr.kro.bbanggil.user.member.dto.request.MemberRequestSignupDto;

public interface FindIdPwService {
	
	 /**
     * 아이디 찾기
     */
    String findUserIdByEmail(MemberRequestSignupDto memberRequestSignupDto);

    /**
     * 임시 비밀번호 생성 및 이메일 발송 
     */
    String sendTemporaryPassword(MemberRequestSignupDto memberRequestSignupDto) throws MessagingException;
	
}

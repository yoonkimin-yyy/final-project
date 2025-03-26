package kr.kro.bbanggil.member.service;

import jakarta.mail.MessagingException;

public interface FindIdPwService {
	
	 /**
     * 아이디 찾기
     */
    String findUserIdByEmail(String userEmail);

    /**
     * 임시 비밀번호 생성 및 이메일 발송 
     */
    String sendTemporaryPassword(String userEmail) throws MessagingException;
	
}

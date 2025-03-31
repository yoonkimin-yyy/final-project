package kr.kro.bbanggil.user.member.api;

public interface SmsService {
	String sendCertificationCode(String phoneNumber); // SMS 인증번호 발송, 저장

	boolean verifyCode(String phoneNumber, String inputCode); // 인증번호 검증
	
}

package kr.kro.bbanggil.common.util;

public interface EmailService {

	
	
	
	public void sendSubscriptionEmail(String toEmail);  // 구독 완료 이메일 발송
	public void sendNewsletterEmail(String toEmail, String subject, String body);
	
	
	
	
}

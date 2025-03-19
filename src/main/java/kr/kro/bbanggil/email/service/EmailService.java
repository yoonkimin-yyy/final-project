package kr.kro.bbanggil.email.service;

public interface EmailService {

	
	
	
	public boolean sendSubscriptionEmail(String email);  // 구독 완료 이메일 발송
	public void sendNewsletterEmail(String toEmail, String subject, String body);
	
	
	
	
}

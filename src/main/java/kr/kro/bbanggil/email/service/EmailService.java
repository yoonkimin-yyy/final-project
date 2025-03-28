package kr.kro.bbanggil.email.service;

import java.util.List;

import kr.kro.bbanggil.email.dto.response.SubscriptionResponseDto;

public interface EmailService {

	
	
	
	public boolean sendSubscriptionEmail(String email);  // 구독 완료 이메일 발송
	public void sendNewsletterEmail(String toEmail, String subject, String body);
	
	 List<SubscriptionResponseDto> getAllSubscribers();
	
	 int getSendSuccessRate();
	
}

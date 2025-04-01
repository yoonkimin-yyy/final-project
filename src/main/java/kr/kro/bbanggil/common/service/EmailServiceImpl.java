package kr.kro.bbanggil.common.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.kro.bbanggil.common.dto.response.SubscriptionResponseDto;
import kr.kro.bbanggil.common.mapper.EmailMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);
	private final JavaMailSender mailSender;
	private final EmailMapper emailMapper;

	// 보내는 사람 이메일
	@Value("${spring.mail.username}")
	private String fromEmail;

	/**
	 * 구독 완료 알림 서비스 보내는 기능
	 */
	@Override
	public boolean sendSubscriptionEmail(String toEmail) {

		try {
			String subject = "구독 완료 안내";
			String body = "<h1>구독이 완료되었습니다!</h1><p>이제부터 정기적인 뉴스레터를 받아보실 수 있습니다.</p>";
			
			sendEmail(toEmail, subject, body);
			
			int exists = emailMapper.checkEmailExists(toEmail);
			
			if (exists > 0) {
	            emailMapper.reactivateSubscription(toEmail);
	        } else {
	            emailMapper.insertSubscriber(toEmail);
	        }
			
			
			logger.info(" 이메일 발송 성공: {}", toEmail);
			return true; // 이메일 발송 성공 시 true 반환
			
			

		} catch (Exception e) {
			logger.error("❌ 이메일 발송 실패: {}, 오류 메시지: {}", toEmail, e.getMessage(), e);
			return false; // 이메일 발송 실패 시 false 반환
		}
	}

	/**
	 * 구독한 사람들에게 뉴스레터 보내는기능
	 */
	@Override
	public void sendNewsletterEmail(String toEmail, String subject, String body) {
		if (subject == null || subject.isEmpty()) {
			subject = "새로운 뉴스레터 도착!";
		}
		sendEmail(toEmail, subject, body);
	}

	/**
	 * 이메일만 보내는 기능
	 */
	@Override
	public void sendEmail(String toEmail, String subject, String body) {

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setFrom(fromEmail);
			helper.setTo(toEmail);
			helper.setSubject(subject);
			helper.setText(body, true);

			mailSender.send(message);
			logger.info("이메일 전송 완료");
		} catch (MessagingException e) {
			throw new RuntimeException("이메일 전송 실패", e);
		}
	}

	@Override
	public List<SubscriptionResponseDto> getAllSubscribers() {
        return emailMapper.selectAllSubscribers();
    }
	@Override
	public int getSendSuccessRate() {
	    int total = emailMapper.countTotalSends();
	    int success = emailMapper.countSuccessfulSends();

	    if (total == 0) return 0; // 나누기 0 방지

	    return (int) ((success / (double) total) * 100); // 소수점 → 정수 %
	}
	
	
}

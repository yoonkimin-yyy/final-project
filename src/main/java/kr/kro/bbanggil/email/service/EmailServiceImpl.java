package kr.kro.bbanggil.email.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);
	private final JavaMailSender mailSender;

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
	private void sendEmail(String toEmail, String subject, String body) {

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setFrom(fromEmail);
			helper.setTo(toEmail);
			helper.setSubject(subject);
			helper.setText(body, true);

			mailSender.send(message);
			System.out.println(" 이메일 발송 성공: " + toEmail);
		} catch (MessagingException e) {
			throw new RuntimeException("이메일 전송 실패", e);
		}
	}

}

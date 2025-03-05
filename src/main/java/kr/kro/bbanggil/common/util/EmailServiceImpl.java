package kr.kro.bbanggil.common.util;

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
	
	private final JavaMailSender mailSender;
	
	// 보내는 사람 이메일
	  @Value("${spring.mail.username}")
	    private String fromEmail;
	  
	  
	  @Override
	  public void sendSubscriptionEmail(String toEmail) {
		  String subject = "구독 완료 안내";
		  String body = "<h1>구독이 완료되었습니다!</h1><p>이제부터 정기적인 뉴스레터를 받아보실 수 있습니다.</p>";
		  sendEmail(toEmail, subject, body);
	  }
	  
	  
	  @Override
	  public void sendNewsletterEmail(String toEmail, String subject, String body) {
		if(subject == null || subject.isEmpty()) {
			subject = "새로운 뉴스레터 도착!";
		}
		  sendEmail(toEmail, subject, body);
	  }
	  
	  private void sendEmail(String toEmail, String subject, String body) {
		  
		  if (subject == null || subject.isEmpty()) {
	            subject = "기본 제목"; 
	        }
		  
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

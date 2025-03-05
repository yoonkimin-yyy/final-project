package kr.kro.bbanggil.common.util;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.kro.bbanggil.mail.mapper.EmailMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NewsletterScheduler {

	private final EmailServiceImpl emailService;
	private final EmailMapper emailMapper;
	
	
	@Scheduled(cron = "0 0 9 * * MON")
	public void sendWeeklyNewsletter() {
		
		String subject = " 이번 주의 새로운 빵집 소식!";
		String body = """ 
				<!DOCTYPE html>
				<html lang="ko">
				<head>
				    <meta charset="UTF-8">
				    <meta name="viewport" content="width=device-width, initial-scale=1.0">
				    <title>이주의 빵 뉴스레터</title>
				    <style>
				        body {
				            font-family: Arial, sans-serif;
				            background-color: #f8f8f8;
				            margin: 0;
				            padding: 0;
				        }
				        .container {
				            max-width: 600px;
				            margin: 20px auto;
				            background: white;
				            padding: 20px;
				            border-radius: 10px;
				            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
				        }
				        h1 {
				            color: #ff6600;
				            text-align: center;
				        }
				        .image {
				            width: 100%;
				            border-radius: 10px;
				        }
				        .content {
				            padding: 15px;
				            font-size: 16px;
				            color: #333;
				            line-height: 1.6;
				        }
				        .button {
				            display: block;
				            width: 80%;
				            margin: 20px auto;
				            padding: 10px;
				            background-color: #ff6600;
				            color: white;
				            text-align: center;
				            font-size: 18px;
				            text-decoration: none;
				            border-radius: 5px;
				        }
				        .footer {
				            text-align: center;
				            font-size: 12px;
				            color: #777;
				            margin-top: 20px;
				        }
				        .footer a {
				            color: #ff6600;
				            text-decoration: none;
				        }
				    </style>
				</head>
				<body>

				<div class="container">
				    <h1>🍞 이주의 추천 빵집</h1>
				    <img src="http://localhost:80/img/common/서천 파티세리 수.jpg" alt="맛있는 빵" class="image">
				    
				    <div class="content">
				        <p>이번 주에도 다양한 빵집 소식과 할인 정보를 전해드립니다! 🥐</p>
				        <p><b>🥖 추천 빵집:</b> "서천 파티세리 수" - 갓 구운 크루아상이 인기!<br>
				           <b>🍩 신상품:</b> 부드러운 크림 도넛 출시!<br>
				           <b>🛍️ 할인 정보:</b> 2개 구매 시 1개 무료 이벤트 진행 중!</p>
				    </div>

				    <a href="https://yourwebsite.com/newsletter" class="button">자세히 보기</a>

				    <div class="footer">
				        <p>뉴스레터 수신을 원하지 않으시다면 <a href="#">구독 해지</a>를 눌러주세요.</p>
				    </div>
				</div>

				</body>
				</html>
				""";
		
		
		List<String> subscriberEmails = emailMapper.getAllSubscriberEmails();
		
		 for (String email : subscriberEmails) {
	            try {
	                emailService.sendNewsletterEmail(email,subject,body);
	                System.out.println(" 뉴스레터 발송 완료: " + email);
	            } catch (Exception e) {
	                System.err.println(" 뉴스레터 발송 실패 (" + email + "): " + e.getMessage());
	            }
	        }
		
		
	}
	
}

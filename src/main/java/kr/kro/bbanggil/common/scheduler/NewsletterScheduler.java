package kr.kro.bbanggil.common.scheduler;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.kro.bbanggil.common.mapper.EmailMapper;
import kr.kro.bbanggil.common.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NewsletterScheduler {
	
	private static final Logger logger = LogManager.getLogger(NewsletterScheduler.class);

	private final EmailServiceImpl emailService;
	private final EmailMapper emailMapper;
	
	
	@Scheduled(cron = "0 0 9 * * MON")
	public void sendWeeklyNewsletter() {
		
		/**
		 * 랜덤 추천 빵집 정보 가져오기
		 */
		Map<String, String> bakeryInfo = emailMapper.getRandomBakery();
		String bakeryName = bakeryInfo.get("BAKERY_NAME");
		String imgNo = bakeryInfo.get("IMG_NO");
		String changeName = bakeryInfo.get("CHANGE_NAME");
		String resourcesPath = bakeryInfo.get("RESOURCES_PATH");
		String openTime = bakeryInfo.get("OPEN_TIME");
		String closeTime = bakeryInfo.get("CLOSE_TIME");
	 

		
		String subject = " 🍞 이번 주의 새로운 빵집:" +bakeryName;
		String body = String.format(""" 
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
                        <p><b>🥖 추천 빵집:</b> "%s" - %s<br></p>
                          <br><p><b>🕒 운영 시간:</b> %s ~ %s</p>
				    </div>

				    <a href="https://yourwebsite.com/newsletter" class="button">자세히 보기</a>

				    <div class="footer">
				        <p>뉴스레터 수신을 원하지 않으시다면 <a href="#">구독 해지</a>를 눌러주세요.</p>
				    </div>
				</div>

				</body>
				</html>
				""",resourcesPath,bakeryName,openTime,closeTime);
		
		
		List<String> subscriberEmails = emailMapper.getAllSubscriberEmails();
		
		 for (String email : subscriberEmails) {
	            try {
	                emailService.sendNewsletterEmail(email,subject,body);
	                logger.info(" 뉴스레터 발송 완료: {}", email);
	            } catch (Exception e) {
	            	emailMapper.insertSendLog(email, "FAIL");
	            	logger.error(" 뉴스레터 발송 실패 ({}): {}", email, e.getMessage(), e);
	            }
	        }
		
		
	}
	
}

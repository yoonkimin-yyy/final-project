package kr.kro.bbanggil.member.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
public class SmsServiceImpl implements SmsService{
	
	 private final Map<String, String> verificationCodes = new HashMap<>();
	 private final Map<String, Long> codeExpiration = new HashMap<>();
	 private final long CODE_EXPIRATION_TIME = 3 * 60 * 1000; // 3분
   
   @Value("${coolsms.apikey}")
    private String apiKey;

    @Value("${coolsms.apisecret}")
    private String apiSecret;

    @Value("${coolsms.fromnumber}")
    private String fromNumber;

    private DefaultMessageService messageService;    
    
    @PostConstruct
    public void init() {
       // coolsms 서비스 초기화
       this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }
    
    @Override
    public String sendCertificationCode(String phoneNumber) {
       // 6자리 랜덤 인증번호 생성
       String code =  generateRandomCode(6);
       
       // 인증번호 저장
       verificationCodes.put(phoneNumber, code);
       codeExpiration.put(phoneNumber, System.currentTimeMillis() + CODE_EXPIRATION_TIME);
       System.out.println("인증번호 [" + code + "] 저장됨. (3분 유효)");
       
       // sms 메세지 작성
       Message message = new Message();
//       message.setFrom(fromNumber); 
//       message.setTo(phoneNumber);
//       message.setText("인증번호는 [" + code + "] 입니다.");
       System.out.println(code);
       
       // 메세지 발송
       messageService.sendOne(new SingleMessageSendingRequest(message));

       return code;
    }
    
    // 인증번호 검증
    @Override
    public boolean verifyCode(String phoneNumber, String inputCode) {
        String savedCode = verificationCodes.get(phoneNumber);
        Long expirationTime = codeExpiration.get(phoneNumber);

        if (savedCode == null || expirationTime == null || System.currentTimeMillis() > expirationTime) {
            System.out.println("인증번호가 만료되었거나 존재하지 않습니다.");
            return false;
        }

        if (savedCode.equals(inputCode)) {
            System.out.println("인증 성공! [" + inputCode + "]");
            verificationCodes.remove(phoneNumber); // 인증 성공 후 삭제
            codeExpiration.remove(phoneNumber);
            return true;
        }

        System.out.println("인증 실패! 입력한 코드: " + inputCode + ", 저장된 코드: " + savedCode);
        return false;
    }

    private String generateRandomCode(int length) {
        // 인증번호 생성(랜덤숫자6)
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for(int i = 0; i<length; i++) {
           code.append(random.nextInt(10));
        }
        return code.toString();
     }
    
   

    
    
}








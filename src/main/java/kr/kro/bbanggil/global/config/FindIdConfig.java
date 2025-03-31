package kr.kro.bbanggil.global.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FindIdConfig {

    // 아이디 마스킹 메서드: 아이디 길이에 따라 반은 보이고 나머지는 마스킹
    public String maskUserId(String userId) {
        if (userId == null || userId.length() <= 1) {
            return userId;  
        }

        // 아이디의 길이를 반으로 나누고 반올림
        int halfLength = (int) Math.ceil(userId.length() / 2.0);  // 반올림한 절반 길이

        // 앞부분은 그대로 보이도록
        String visiblePart = userId.substring(0, halfLength);

        // 나머지 부분은 '*'로 마스킹
        String maskedPart = "*".repeat(userId.length() - halfLength);

        return visiblePart + maskedPart;  // 앞부분 + 마스킹된 뒷부분
    }
}

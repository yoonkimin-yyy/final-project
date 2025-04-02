package kr.kro.bbanggil.user.member.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordUtil {

    @Value("${password.characters}")
    private String characters;

    @Value("${password.length}")
    private int passwordLength;

    private final SecureRandom random = new SecureRandom();

    public String generateTempPassword() {
        StringBuilder sb = new StringBuilder(passwordLength);
        for (int i = 0; i < passwordLength; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}

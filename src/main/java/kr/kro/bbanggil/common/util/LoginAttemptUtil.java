package kr.kro.bbanggil.common.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;

@Component
public class LoginAttemptUtil {

    private static final int MAX_ATTEMPTS = 5;
    private static final int LOCK_TIME_MINUTES = 3;

    // 로그인 실패 횟수와 잠금 시간을 세션에서 관리
    public boolean isAccountLocked(HttpSession session) {
        Integer failedAttempts = (Integer) session.getAttribute("failedAttempts");
        LocalDateTime lockTime = (LocalDateTime) session.getAttribute("lockTime");

        // 잠금 시간이 지난 경우, 실패 횟수 초기화
        if (lockTime != null && ChronoUnit.MINUTES.between(lockTime, LocalDateTime.now()) > LOCK_TIME_MINUTES) {
            session.setAttribute("failedAttempts", 0);
            session.setAttribute("lockTime", null);
            return false; // 잠금 해제 상태
        }

        // 5회 이상 실패한 경우
        return failedAttempts != null && failedAttempts >= MAX_ATTEMPTS;
    }

    // 실패 횟수 업데이트
    public void incrementFailedAttempts(HttpSession session) {
        Integer failedAttempts = (Integer) session.getAttribute("failedAttempts");
        failedAttempts = (failedAttempts == null) ? 1 : failedAttempts + 1;
        session.setAttribute("failedAttempts", failedAttempts);

        // 5회 이상 실패한 경우 잠금 시간 설정
        if (failedAttempts >= MAX_ATTEMPTS) {
            session.setAttribute("lockTime", LocalDateTime.now());
        }
    }

    // 로그인 성공 시 실패 횟수 초기화
    public void resetFailedAttempts(HttpSession session) {
        session.setAttribute("failedAttempts", 0);
        session.setAttribute("lockTime", null);
    }
}

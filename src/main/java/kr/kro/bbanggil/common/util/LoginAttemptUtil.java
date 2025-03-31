package kr.kro.bbanggil.common.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
public class LoginAttemptUtil {

    private static final int MAX_ATTEMPTS = 5;
    private static final int LOCK_TIME_MINUTES = 3;
    
    // IP 주소를 키로, 로그인 실패 횟수 및 잠금 시간 관리를 위한 맵
    private ConcurrentHashMap<String, LoginAttemptInfo> attemptsMap = new ConcurrentHashMap<>();
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class LoginAttemptInfo {
        private int failedAttempts;  // 실패 횟수
        private LocalDateTime lockTime;  // 잠금 시간

        // 실패 횟수 증가
        public void incrementFailedAttempts() {
            this.failedAttempts++;
        }

        // 실패 횟수 초기화
        public void resetFailedAttempts() {
            this.failedAttempts = 0;
        }
    }
    
 // IP 주소를 기준으로 계정 잠금 상태 확인
    public boolean isAccountLocked(HttpServletRequest request) {
        String clientIp = getClientIp(request);
        LoginAttemptInfo attemptInfo = attemptsMap.get(clientIp);

        if (attemptInfo == null) {
            return false;  // 로그인 시도 없음
        }

        // 잠금 시간이 지난 경우 실패 횟수 초기화
        if (attemptInfo.getLockTime() != null && ChronoUnit.MINUTES.between(attemptInfo.getLockTime(), LocalDateTime.now()) > LOCK_TIME_MINUTES) {
            attemptInfo.resetFailedAttempts();
            attemptInfo.setLockTime(null);  // 잠금 시간 초기화
            return false;  // 잠금 해제
        }

        // 실패 횟수가 5회 이상이면 잠금 상태
        return attemptInfo.getFailedAttempts() >= MAX_ATTEMPTS;
    }

    // 로그인 실패 횟수 증가
    public void incrementFailedAttempts(HttpServletRequest request) {
        String clientIp = getClientIp(request);
        LoginAttemptInfo attemptInfo = attemptsMap.getOrDefault(clientIp, new LoginAttemptInfo(0, null));

        attemptInfo.incrementFailedAttempts();
        if (attemptInfo.getFailedAttempts() >= MAX_ATTEMPTS) {
            attemptInfo.setLockTime(LocalDateTime.now());  // 잠금 시간 설정
        }

        attemptsMap.put(clientIp, attemptInfo);
    }

    // 로그인 성공 시 실패 횟수 초기화
    public void resetFailedAttempts(HttpServletRequest request) {
        String clientIp = getClientIp(request);
        LoginAttemptInfo attemptInfo = attemptsMap.get(clientIp);

        if (attemptInfo != null) {
            attemptInfo.resetFailedAttempts();  // 실패 횟수 초기화
            attemptInfo.setLockTime(null);  // 잠금 시간 초기화
        }
    }

    // 클라이언트의 IP 주소 추출
    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null) {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }
    

//    // 로그인 실패 횟수와 잠금 시간을 세션에서 관리
//    public boolean isAccountLocked(HttpSession session) {
//        Integer failedAttempts = (Integer) session.getAttribute("failedAttempts");
//        LocalDateTime lockTime = (LocalDateTime) session.getAttribute("lockTime");
//
//        // 잠금 시간이 지난 경우, 실패 횟수 초기화
//        if (lockTime != null && ChronoUnit.MINUTES.between(lockTime, LocalDateTime.now()) > LOCK_TIME_MINUTES) {
//            session.setAttribute("failedAttempts", 0);
//            session.setAttribute("lockTime", null);
//            return false; // 잠금 해제 상태
//        }
//
//        // 5회 이상 실패한 경우
//        return failedAttempts != null && failedAttempts >= MAX_ATTEMPTS;
//    }
//
//    // 실패 횟수 업데이트
//    public void incrementFailedAttempts(HttpSession session) {
//        Integer failedAttempts = (Integer) session.getAttribute("failedAttempts");
//        failedAttempts = (failedAttempts == null) ? 1 : failedAttempts + 1;
//        session.setAttribute("failedAttempts", failedAttempts);
//
//        // 5회 이상 실패한 경우 잠금 시간 설정
//        if (failedAttempts >= MAX_ATTEMPTS) {
//            session.setAttribute("lockTime", LocalDateTime.now());
//        }
//    }
//
//    // 로그인 성공 시 실패 횟수 초기화
//    public void resetFailedAttempts(HttpSession session) {
//        session.setAttribute("failedAttempts", 0);
//        session.setAttribute("lockTime", null);
//    }
}

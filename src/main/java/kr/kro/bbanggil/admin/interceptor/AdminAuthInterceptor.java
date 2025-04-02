package kr.kro.bbanggil.admin.interceptor;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.kro.bbanggil.admin.service.AdminMainServiceImpl;
import kr.kro.bbanggil.global.exception.BbanggilException;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor{
	
	private final Logger logger = LogManager.getLogger(AdminMainServiceImpl.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) throws IOException {
		HttpSession session = request.getSession();
		Object userRole = session.getAttribute("role");
		if(userRole==null || !userRole.equals("admin")) {
			logger.error("session: '{}', userRole: '{}'", session, userRole);
			throw new BbanggilException("Admin이 아닙니다","common/error",HttpStatus.NOT_ACCEPTABLE);
		}
		return true;
	}
	
}

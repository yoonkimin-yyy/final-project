package kr.kro.bbanggil.admin.interceptor;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.kro.bbanggil.admin.exception.AdminException;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) throws IOException {
		HttpSession session = request.getSession();
		Object userRole = session.getAttribute("role");
		if(userRole==null || !userRole.equals("admin")) {
			throw new AdminException("Admin이 아닙니다","common/error",HttpStatus.NOT_ACCEPTABLE);
		}
		return true;
	}
	
}

package kr.kro.bbanggil.user.bakery.interceptor;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class OwnerInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) throws IOException {
		HttpSession session = request.getSession();
		Object userRole = session.getAttribute("role");
		if(userRole==null || !userRole.equals("owner")) {
			response.sendRedirect("/");
			return false;  
		}
		return true;
	}

}

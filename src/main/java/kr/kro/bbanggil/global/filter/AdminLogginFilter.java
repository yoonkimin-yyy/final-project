package kr.kro.bbanggil.global.filter;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.kro.bbanggil.admin.service.AdminServiceImpl;

@Component
@WebFilter(urlPatterns = { "/admin/form", "/admin/bakery/detail" })
public class AdminLogginFilter extends HttpFilter {
	
	private final Logger logger = LogManager.getLogger(AdminServiceImpl.class); 

	// 관리자 사이트 필터
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
					
		HttpSession session = request.getSession();
		
		Object userId = session.getAttribute("userId");
		Object userRole = session.getAttribute("role");

		String path = request.getServletPath();
		
		 logger.info("Request received: Path = {}, User ID = {}, User Role = {}", path, userId, userRole);

		chain.doFilter(request, response);
	}

	private boolean isAdmin(Object userRole) {
		return "admin".equals(userRole);
	}

}

package kr.kro.bbanggil.global.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
//@WebFilter("/register/login")
@WebFilter(urlPatterns = { "/admin/form", "/admin/bakery/detail" })
public class AdminLogginFilter extends HttpFilter {

	// 관리자 사이트 필터
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//System.out.println("::필터:::::");
		HttpSession session = request.getSession();
		//System.out.println("::필터:::::" + session);
		Object userId = session.getAttribute("userId");
		Object userRole = session.getAttribute("role");
		//System.out.println("::필터:::::" + userId);
		//System.out.println("::필터:::::" + userRole);

		String path = request.getServletPath();
		//System.out.println("::필터:::::" + path);

		/*
		 * if (!isAdmin(userRole)) {
		 * System.out.println("::필터::::::관리자 권한 없음, 로그인 페이지로 리다이렉트");
		 * response.sendRedirect("/admin/login"); return; }
		 */

		chain.doFilter(request, response);
	}

	private boolean isAdmin(Object userRole) {
		return "admin".equals(userRole);
	}

	/*
	 * private boolean isExcludedPath(String path) { return
	 * path.equals("/admin/form") || path.equals("/admin/bakery/detail") ||
	 * path.equals("/admin/user/detail") || path.equals("/admin/bakery/accept"); }
	 */

}

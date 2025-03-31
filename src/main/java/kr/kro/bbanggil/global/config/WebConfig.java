package kr.kro.bbanggil.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.kro.bbanggil.admin.interceptor.AdminAuthInterceptor;
import kr.kro.bbanggil.user.bakery.interceptor.OwnerInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer{
	
	private final OwnerInterceptor ownerInterceptor;
	private final AdminAuthInterceptor adminAuthInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		//사장인지 확인하는 인터셉터
		registry.addInterceptor(ownerInterceptor)  // 인터셉터 등록
				.addPathPatterns("/bakery/insert/**"); // 검사할 url
		
		//admin권한이 있는지 확인하는 인터셉터
		registry.addInterceptor(adminAuthInterceptor)
				.addPathPatterns("/admin/**")
				.excludePathPatterns("/admin/login");
				
	}
	
}

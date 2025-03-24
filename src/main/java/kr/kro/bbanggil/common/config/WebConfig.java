package kr.kro.bbanggil.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.kro.bbanggil.bakery.interceptor.OwnerInterceptor;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer{
	
	private final OwnerInterceptor ownerInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(ownerInterceptor)  // 인터셉터 등록
				.addPathPatterns("/bakery/insert/**"); // 검사할 url
	}
}

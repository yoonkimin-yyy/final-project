package kr.kro.bbanggil.global.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Getter
@Configuration
@PropertySource("classpath:/application.properties")
public class IamportApiProperty {
	
	@Value("${rest_api_key}")
	private String impKey;
	
	@Value("${rest_api_secret}")
	private String impSecret;
	
}

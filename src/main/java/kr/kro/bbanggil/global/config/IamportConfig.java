package kr.kro.bbanggil.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.siot.IamportRestClient.IamportClient;

import kr.kro.bbanggil.global.properties.IamportApiProperty;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class IamportConfig {
	private final IamportApiProperty iamportApiProperty;
	
	@Bean
	public IamportClient iamportClient() {
		return new IamportClient(iamportApiProperty.getImpKey(), iamportApiProperty.getImpSecret());
	}
	
}

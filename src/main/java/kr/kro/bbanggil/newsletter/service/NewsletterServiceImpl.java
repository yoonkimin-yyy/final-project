package kr.kro.bbanggil.newsletter.service;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.admin.dto.response.NewsletterResponseDto;
import kr.kro.bbanggil.email.mapper.NewsletterMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsletterServiceImpl implements NewsletterService{

	
	private final NewsletterMapper newsletterMapper;
	
	@Override
	public NewsletterResponseDto getLatestNewsletter() {
		
		return   newsletterMapper.selectLatestNewsletter();
	}
}

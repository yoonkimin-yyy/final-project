package kr.kro.bbanggil.newsletter.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.admin.dto.response.NewsletterResponseDto;
import kr.kro.bbanggil.common.mapper.NewsletterMapper;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class NewsletterServiceImpl implements NewsletterService{

	
	private final NewsletterMapper newsletterMapper;
	
	@Override
	public NewsletterResponseDto getLatestNewsletter() {
		
		return   newsletterMapper.selectLatestNewsletter();
	}
	
	 @Override
	    public String calculateNextNewsletterSchedule() {
	        LocalDate today = LocalDate.now();
	        int daysUntilNextMonday = (DayOfWeek.MONDAY.getValue() - today.getDayOfWeek().getValue() + 7) % 7;
	        if (daysUntilNextMonday == 0) daysUntilNextMonday = 7;

	        LocalDate nextMonday = today.plusDays(daysUntilNextMonday);
	        LocalDateTime nextNewsletterTime = LocalDateTime.of(nextMonday, LocalTime.of(9, 0));
	        return nextNewsletterTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	    }
}

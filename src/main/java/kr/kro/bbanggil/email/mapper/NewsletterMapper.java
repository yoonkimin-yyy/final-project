package kr.kro.bbanggil.email.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.kro.bbanggil.admin.dto.response.NewsletterResponseDto;

@Mapper
public interface NewsletterMapper {

	NewsletterResponseDto selectLatestNewsletter();
}

package kr.kro.bbanggil.common.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.kro.bbanggil.admin.dto.response.NewsletterResponseDto;

@Mapper
public interface NewsletterMapper {

	NewsletterResponseDto selectLatestNewsletter();
}

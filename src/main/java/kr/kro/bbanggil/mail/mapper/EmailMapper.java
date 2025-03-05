package kr.kro.bbanggil.mail.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {

	
	List<String> getAllSubscriberEmails();
	
	
}

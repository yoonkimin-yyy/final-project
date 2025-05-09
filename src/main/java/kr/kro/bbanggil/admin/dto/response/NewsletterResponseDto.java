package kr.kro.bbanggil.admin.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class NewsletterResponseDto {

	private int historyNo;
	private String bakeryName;
	private String resourcesPath;
	private String changeName;
	private String openTime;
	private String closeTime;
	private LocalDateTime sendDate;
	private String location; 
	
}

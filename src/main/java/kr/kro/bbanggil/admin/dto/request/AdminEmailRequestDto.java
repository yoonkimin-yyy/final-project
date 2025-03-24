package kr.kro.bbanggil.admin.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminEmailRequestDto {
	
	private String address;
	private String title;
	private String content;
	
}

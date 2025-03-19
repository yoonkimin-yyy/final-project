package kr.kro.bbanggil.bakery.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BakeryResponseScheduleDto {

	
	private String day;
	private String openTime;
	private String closeTime;
}

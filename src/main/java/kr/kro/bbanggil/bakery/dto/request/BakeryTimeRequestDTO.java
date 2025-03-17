package kr.kro.bbanggil.bakery.dto.request;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import kr.kro.bbanggil.bakery.dto.BakeryTimeSetDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Valid
public class BakeryTimeRequestDTO {
	@Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]~([01][0-9]|2[0-3]):[0-5][0-9]$", 
			 message = "시간 형식은 HH:mm~HH:mm (예: 09:00~21:00)이어야 합니다.")
	private String weekday;
	
	@Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]~([01][0-9]|2[0-3]):[0-5][0-9]$", 
			 message = "시간 형식은 HH:mm~HH:mm (예: 09:00~21:00)이어야 합니다.")
	private String weekend;
	
	@Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]~([01][0-9]|2[0-3]):[0-5][0-9]$", 
			 message = "시간 형식은 HH:mm~HH:mm (예: 09:00~21:00)이어야 합니다.")
	private String monday;
	
	@Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]~([01][0-9]|2[0-3]):[0-5][0-9]$", 
			 message = "시간 형식은 HH:mm~HH:mm (예: 09:00~21:00)이어야 합니다.")
	private String tuesday;
	
	@Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]~([01][0-9]|2[0-3]):[0-5][0-9]$", 
			 message = "시간 형식은 HH:mm~HH:mm (예: 09:00~21:00)이어야 합니다.")
	private String wednesday;
	
	@Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]~([01][0-9]|2[0-3]):[0-5][0-9]$", 
			 message = "시간 형식은 HH:mm~HH:mm (예: 09:00~21:00)이어야 합니다.")
	private String thursday;
	
	@Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]~([01][0-9]|2[0-3]):[0-5][0-9]$", 
			 message = "시간 형식은 HH:mm~HH:mm (예: 09:00~21:00)이어야 합니다.")
	private String friday;
	
	@Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]~([01][0-9]|2[0-3]):[0-5][0-9]$", 
			 message = "시간 형식은 HH:mm~HH:mm (예: 09:00~21:00)이어야 합니다.")
	private String saturday;

	@Pattern(regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]~([01][0-9]|2[0-3]):[0-5][0-9]$", 
			 message = "시간 형식은 HH:mm~HH:mm (예: 09:00~21:00)이어야 합니다.")
	private String sunday;
	
	private List<BakeryTimeSetDTO> setDTO = new ArrayList<>();
	
	public void dateSet() {
		if(weekday!=null&&weekend!=null) {
			if(!weekday.isEmpty()) {
				monday = weekday;
				tuesday = weekday;
				wednesday = weekday;
				thursday = weekday;
				friday = weekday;
			}
			if(!weekend.isEmpty()) {
				saturday = weekend;
				sunday = weekend;
			}
		}
	}
	
	
	public void timeSet() {
		String[] week = {monday,tuesday,wednesday,thursday,friday,saturday,sunday};
		String[] days = {"월","화","수","목","금","토","일"};
		for(int i=0; i<week.length;i++) {
			BakeryTimeSetDTO timeSet = new BakeryTimeSetDTO();
			String[] result = week[i].split("~");
			timeSet.setStart(result[0]);
			timeSet.setEnd(result[1]);
			timeSet.setDay(days[i]);
			setDTO.add(timeSet);
		}
	}
}

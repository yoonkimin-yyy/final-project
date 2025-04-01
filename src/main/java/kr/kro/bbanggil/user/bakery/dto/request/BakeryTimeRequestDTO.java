package kr.kro.bbanggil.user.bakery.dto.request;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import kr.kro.bbanggil.user.bakery.dto.BakeryTimeSetDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Valid
public class BakeryTimeRequestDTO {
	private String weekday;
	
	private String weekend;
	
	private String monday;
	
	private String tuesday;
	
	private String wednesday;
	
	private String thursday;
	
	private String friday;
	
	private String saturday;

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

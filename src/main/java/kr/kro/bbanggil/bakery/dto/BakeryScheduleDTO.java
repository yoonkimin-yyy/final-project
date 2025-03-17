package kr.kro.bbanggil.bakery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BakeryScheduleDTO {
	private int scheduleNo;
	private int bakeryNo;
	private String bakeryDay;
	private String bakeryOpenTime;
	private String bakeryCloseTime;
}

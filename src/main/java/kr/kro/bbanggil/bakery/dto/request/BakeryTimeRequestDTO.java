package kr.kro.bbanggil.bakery.dto.request;

import lombok.Getter;

@Getter
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
}

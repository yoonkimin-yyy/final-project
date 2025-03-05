package kr.kro.bbanggil.bakery.response.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class BakeryResponseMenuDto {
		
	private int menuNo;
	private String menuName;
	private double menuPrice;
	private String menuInfo;
	private String categoryName;
	
	
}

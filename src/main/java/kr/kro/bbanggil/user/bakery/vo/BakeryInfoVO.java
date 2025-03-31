package kr.kro.bbanggil.user.bakery.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class BakeryInfoVO {
	private String bakeryName;
	private String bakeryAddress;
	private String bakeryPhone;
	private double latitude;
	private double longitude;
	private String region;
	
	@Setter
	private int bakeryNo;
}

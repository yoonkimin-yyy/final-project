package kr.kro.bbanggil.user.bakery.vo;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class BakeryDetailVO {
	private String amenity;
	private String insideInfo;
	private String outsideInfo;
	private String createdDate;
	private int bakeryNo;
}

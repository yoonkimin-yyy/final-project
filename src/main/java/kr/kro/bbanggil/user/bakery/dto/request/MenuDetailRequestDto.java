package kr.kro.bbanggil.user.bakery.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDetailRequestDto {
	private int menuNo;
	private String menuName;
	private int menuPrice;
	private  String menuInfo;
	private int bakeryNo;
	private int categoryNo;
	private int menuCount;
}

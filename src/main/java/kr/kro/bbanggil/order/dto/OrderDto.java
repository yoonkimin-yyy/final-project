package kr.kro.bbanggil.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDto {
	
	private String menuName;
	private int menuPrice;
	private int menuCount;
	private String bakeryName;
	
}

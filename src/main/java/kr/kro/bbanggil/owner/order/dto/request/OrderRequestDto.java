package kr.kro.bbanggil.owner.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {
	
	private int listNo;
	private int cartNo;
	private String bakeryName;
	private String menuNo;
	private int menuCount;
	private int menuPrice;

	private PickupCheckRequestDto pickDto = new PickupCheckRequestDto();
    
    
}

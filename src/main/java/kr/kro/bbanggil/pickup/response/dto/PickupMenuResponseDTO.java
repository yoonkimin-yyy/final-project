package kr.kro.bbanggil.pickup.response.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PickupMenuResponseDTO {

	private String menuName;
	private int menuCount;
	private int price;
	
}

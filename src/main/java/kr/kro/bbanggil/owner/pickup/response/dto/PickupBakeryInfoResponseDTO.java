package kr.kro.bbanggil.owner.pickup.response.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PickupBakeryInfoResponseDTO {

	private int bakeryNo;
	private PickupPaymentResponseDTO payDTO = new PickupPaymentResponseDTO();
	private PickupStatusResponseDTO statusDTO = new PickupStatusResponseDTO();
	private PickupUserInfoResponseDTO userDTO = new PickupUserInfoResponseDTO();
	private List<PickupMenuResponseDTO> menuDTO = new ArrayList<PickupMenuResponseDTO>();
	private PickupSalesResponseDTO salesDTO = new PickupSalesResponseDTO();
	
}

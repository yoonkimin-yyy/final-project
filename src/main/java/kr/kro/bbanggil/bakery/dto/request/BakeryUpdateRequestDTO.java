package kr.kro.bbanggil.bakery.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BakeryUpdateRequestDTO {
	private String bakeryName;
	private String bakeryAddress;
	private String bakeryPhone;
	private String createdDate;
	private String insideInfo;
	private String outsideInfo;
	private String parkingInfo;
	
	
	
	BakeryTimeRequestDTO timeDTO = new BakeryTimeRequestDTO();
	BakeryInsertImgRequestDTO imgDTO = new BakeryInsertImgRequestDTO();
}

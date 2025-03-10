package kr.kro.bbanggil.bakery.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BakeryInsertRequestDTO {
	private String bakeryName;
	private String bakeryAddress;
	private String bakeryPhone;
	private String createdDate;
	private String insideInfo;
	private String outsideInfo;
	private String parkingInfo;
	private String imgLocation;
	private int bakeryNo;
	
	BakeryTimeRequestDTO timeDTO = new BakeryTimeRequestDTO();
	FileRequestDTO fileDTO = new FileRequestDTO();
}

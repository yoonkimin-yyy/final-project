package kr.kro.bbanggil.admin.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class myBakeryResponseDTO {
	private String bakeryName;
	private String bakeryPhone;
	private String bakeryAddress;
	private String InsideInfo;
	private String OutsideInfo;
	private String ParkingInfo;
	private String agree;
	private String reject;
}

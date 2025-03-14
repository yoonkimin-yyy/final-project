package kr.kro.bbanggil.bakery.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import kr.kro.bbanggil.bakery.dto.BakeryTimeSetDTO;
import kr.kro.bbanggil.bakery.dto.request.FileRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class bakeryUpdateResponseDTO {
	
	private int bakeryNo;
	private String bakeryName;
	private String bakeryAddress;
	private String bakeryPhone;
	private String createdDate;
	private String insideInfo;
	private String outsideInfo;
	private String parkingInfo;
	
	private String monday;
	private String tuesday;
	private String wednesday;
	private String thursday;
	private String friday;
	private String saturday;
	private String sunday;
	private String weekday;
	private String weekend;
	
	List<FileRequestDTO> imgDTO = new ArrayList<>();
	
	
}

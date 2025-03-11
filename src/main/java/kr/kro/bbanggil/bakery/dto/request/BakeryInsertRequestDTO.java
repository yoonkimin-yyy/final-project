package kr.kro.bbanggil.bakery.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kr.kro.bbanggil.bakery.dto.BakeryTimeSetDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Valid
public class BakeryInsertRequestDTO {
	
	@Pattern(regexp="^[가-힣]{2,10}$", message="한글만 가능합니다.")
	private String bakeryName;
	
	
	@NotBlank(message = "주소는 비어있으면 안됩니다")
	private String bakeryAddress;
	
	@Pattern(regexp="\\d{3}-\\d{3,4}-\\d{4}", message="전화번호 형식이 올바르지 않습니다.")
	private String bakeryPhone;
	
	private String createdDate;
	
	@NotBlank(message ="내부 정보는 비어있으면 안됩니다.")
	private String insideInfo;
	
	@NotBlank(message ="외부 정보는 비어있으면 안됩니다.")
	private String outsideInfo;
	
	@NotBlank(message ="주차장 정보는 비어있으면 안됩니다.")
	private String parkingInfo;
	private String imgLocation;
	private int bakeryNo;
	
	BakeryTimeRequestDTO timeDTO = new BakeryTimeRequestDTO();
	FileRequestDTO fileDTO = new FileRequestDTO();
	
	public void setTime() {
		timeDTO.dateSet();
		timeDTO.timeSet();
	}
	public List<BakeryTimeSetDTO> getTime() {
		return timeDTO.getSetDTO();
	}
}

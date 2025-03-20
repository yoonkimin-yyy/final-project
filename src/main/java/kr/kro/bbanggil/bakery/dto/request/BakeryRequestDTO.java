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
public class BakeryRequestDTO {
	
	@Pattern(regexp="^[가-힣a-zA-Z\\s]{2,10}$", message="한글,영어 소문자, 영어 대문자, 띄어쓰기만 가능합니다.특수문자는 불가능합니다.")
	private String bakeryName;
	
	@NotBlank(message = "주소는 비어있으면 안됩니다")
	private String bakeryAddress;
	
	@Pattern(regexp="^(010|011|016|017|018|019)\\d{7,8}$|^(0507)\\d{8}$", message="전화번호 형식이 올바르지 않습니다. 전화번호는 010으로 시작하거나 0507로 시작해야합니다")
	private String bakeryPhone;
	
	private String createdDate;
	
	@NotBlank(message ="가게 내부 정보는 비어있으면 안됩니다.")
	private String insideInfo;
	
	@NotBlank(message ="가게 외부 정보는 비어있으면 안됩니다.")
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

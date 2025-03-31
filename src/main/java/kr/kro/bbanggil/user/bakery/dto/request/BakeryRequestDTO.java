package kr.kro.bbanggil.user.bakery.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kr.kro.bbanggil.user.bakery.dto.BakeryTimeSetDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Valid
public class BakeryRequestDTO {
	
	@Pattern(regexp="^[0-9가-힣a-zA-Z\\s]{2,30}$", message="한글,숫자,영어 소문자, 영어 대문자, 띄어쓰기만 가능합니다.특수문자는 불가능합니다.")
	private String bakeryName;
	
	@NotBlank(message = "주소는 비어있으면 안됩니다")
	private String bakeryAddress;
	
	@Pattern(regexp="^\\d{9,12}$", message="전화번호 형식이 올바르지 않습니다. -없이 숫자만 입력해야합니다")
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

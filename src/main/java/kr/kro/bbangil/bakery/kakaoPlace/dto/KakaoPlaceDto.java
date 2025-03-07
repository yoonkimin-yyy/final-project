package kr.kro.bbangil.bakery.kakaoPlace.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoPlaceDto {

	@JsonProperty("place_name")
	private  String placeName;
	
	@JsonProperty("x")
	private  double x; 
	
	@JsonProperty("y")
	private  double y; 
	
	@JsonProperty("road_address_name")
	private  String roadAddressName;
	
	@JsonProperty("phone")
	private  String phone;
	
	 @JsonProperty("address_name")  
	 private String addressName;
	
	 @JsonProperty("category_group_code")  
	 private String categoryGroupCode;
}

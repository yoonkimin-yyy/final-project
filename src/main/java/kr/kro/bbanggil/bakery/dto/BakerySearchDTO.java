package kr.kro.bbanggil.bakery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BakerySearchDTO {
	private String category = "bakeryName";
	private String searchText = "";
}

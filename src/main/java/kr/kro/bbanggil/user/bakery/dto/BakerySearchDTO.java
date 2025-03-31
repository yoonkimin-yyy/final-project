package kr.kro.bbanggil.user.bakery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BakerySearchDTO {
	private String category = "bakeryName";
	private String searchText = "";
	private String keyword1;
	private String keyword2;
}

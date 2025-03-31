package kr.kro.bbanggil.user.bakery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BakeryReviewDTO {
	private int reviewNo;
	private int orderNo;
	private int userNo;
	private String reviewDetail;
	private double reviewRating;
	private String reviewDate;
	private int bakeryNo;
}

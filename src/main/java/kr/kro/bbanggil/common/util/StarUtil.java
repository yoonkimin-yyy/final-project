package kr.kro.bbanggil.common.util;

import org.springframework.stereotype.Component;

@Component
public class StarUtil {
	
	
	public String getStarHtml(Double rating) {
	    if (rating == null || rating == 0) {
	        return "<span class='no-review'>리뷰 없음</span>";
	    }

	    int fullStars = (int) rating.doubleValue();
	    boolean hasHalfStar = rating - fullStars >= 0.25 && rating - fullStars < 0.75;
	    boolean roundedUp = rating - fullStars >= 0.75;
	    StringBuilder sb = new StringBuilder();

	    for (int i = 0; i < fullStars; i++) {
	        sb.append("<i class='fas fa-star'></i>");
	    }
	    if (hasHalfStar) {
	        sb.append("<i class='fas fa-star-half-alt'></i>");
	    } else if (roundedUp && fullStars < 5) {
	        sb.append("<i class='fas fa-star'></i>");
	    }

	    int totalShown = fullStars + (hasHalfStar || roundedUp ? 1 : 0);
	    for (int i = totalShown; i < 5; i++) {
	        sb.append("<i class='far fa-star'></i>");
	    }

	    return sb.toString();
	}

}

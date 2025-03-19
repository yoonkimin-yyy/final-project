package kr.kro.bbanggil.bakery.review.response.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
	
	private int reviewNo;
	private int userNo;  // 리뷰 작성자 no
	private String userId;
	private double reviewRating;
	private String reviewDetail;
	private MultipartFile reviewImage;
	private int bakeryNo;
	private String reviewDate;
	private String changeName;
	private String resourcesPath;
	private String reviewImageName;
	private String deleteImages;
	private int imgNo;
	private String localPath;
	
	
	private String tagFirst;   // 첫 번째 태그
	private String tagSecond;  // 두 번째 태그
	private String tagThird;   // 세 번째 태그
	private String tagForth;   // 네 번째 태그
	private String tagFive;    // 다섯 번째 태그
	private int viewCount;     // 조회수
		
}

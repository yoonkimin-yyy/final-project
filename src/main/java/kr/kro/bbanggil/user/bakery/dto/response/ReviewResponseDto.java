package kr.kro.bbanggil.user.bakery.dto.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.kro.bbanggil.user.bakery.dto.ReviewImageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {
	
	@Setter
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
	
	
	public List<String> getTags() {
	    List<String> tags = new ArrayList<>();
	    if (tagFirst != null && !tagFirst.isEmpty()) tags.add(tagFirst);
	    if (tagSecond != null && !tagSecond.isEmpty()) tags.add(tagSecond);
	    if (tagThird != null && !tagThird.isEmpty()) tags.add(tagThird);
	    if (tagForth != null && !tagForth.isEmpty()) tags.add(tagForth);
	    if (tagFive != null && !tagFive.isEmpty()) tags.add(tagFive);
	    return tags;
	}
	private String reviewReply;
	private String reviewReplyDate;
	
	private List<ReviewImageDto> reviewImages = new ArrayList<>();
	
	
		
}

package kr.kro.bbanggil.mypage.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MypageReviewTagResponseDto {
	
	/**
	 * 리뷰태그 테이블
	 * 태그 번호
	 * 리뷰번호
	 * 빵이 맛있어요
	 * 친절해요
	 * 아이와 가기 좋아요
	 * 매장이 청결해요
	 * 특별한 메뉴가 있어요
	 */
	private int tagNo;
	private int reviewNo;
	private String tagFirst;
	private String tagSecond;
	private String tagThird;
	private String tagForth;
	private String tagFive;
	
}

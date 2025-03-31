package kr.kro.bbanggil.user.member.dto.response;

import lombok.Getter;

@Getter
public class MypageReviewResponseDto {
	
	/**
	 * 리뷰 테이블
	 * 리뷰 번호
	 * 주문 번호
	 * 유저 번호
	 * 리뷰 내용
	 * 리뷰 별점
	 * 리뷰 작성일
	 * 빵집 번호
	 */	
	private int reviewNo;
	private int orderNo;
	private int userNo;
	private String reviewDetail;
	private String reviewRating;
	private String reviewDate;
	private String bakeryNo;
	
}

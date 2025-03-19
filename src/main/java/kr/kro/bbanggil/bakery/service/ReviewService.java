package kr.kro.bbanggil.bakery.service;


import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import kr.kro.bbanggil.bakery.review.request.dto.ReviewRequestDto;
import kr.kro.bbanggil.bakery.review.response.dto.PageResponseDto;
import kr.kro.bbanggil.bakery.review.response.dto.ReviewResponseDto;


public interface ReviewService {

	public void writeReview(ReviewRequestDto reviewDto);
	
	
	public int getTotalReviewCount(double bakeryNo);
	
	Map<String, Object> list(PageResponseDto pageInfo, int currentPage, int postCount, int pageLimit, int boardLimit,double bakeryNo,String sort);
	
	
	List<ReviewResponseDto> getReviewImages(double bakeryNo);
	
	
	ReviewResponseDto getReviewId(int reviewNo);
	
	int editReview(ReviewRequestDto reviewRequestDto, List<MultipartFile> files);
	
	int deleteReview(int reviewNo,int userNo, String fileName);
	
	Map<String,Integer> getTagCounts(double bakeryNo);
	
}

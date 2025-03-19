package kr.kro.bbanggil.bakery.review.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.bakery.review.request.dto.FileRequestDto;
import kr.kro.bbanggil.bakery.review.request.dto.ReviewRequestDto;
import kr.kro.bbanggil.bakery.review.response.dto.PageResponseDto;
import kr.kro.bbanggil.bakery.review.response.dto.ReviewResponseDto;

@Mapper
public interface ReviewMapper {

	
	public void insertReview(ReviewRequestDto reviewDto);
	public int checkOrderExists(@Param("orderNo") int orderNo);
	public Integer getLatestBakeryNo();
	public int findCartNoByUserNo(@Param("userNo") int userNo);
	public int findOrderNobyCartNo(@Param("cartNo") int cartNo);
	public void insertReviewImage(FileRequestDto fileRequestDto);
	public void insertReviewTag(ReviewRequestDto reviewDto);
	
	
	public int getTotalCount(double no);
	
	List<ReviewResponseDto> list(@Param("pi") PageResponseDto pageInfo ,
								@Param("bakeryNo")double bakeryNo,
								@Param("orderBy") String orderBy);
	public List<ReviewResponseDto> getReviewImages(double bakeryNo);
	
	
	
	
	
	
	
	
	
	
	// 기존 리뷰 정보 가져오기 (리뷰 내용 + 기존 이미지 리스트)
	public ReviewResponseDto getReviewById(@Param("reviewNo")int reviewNo);
	
	 // 리뷰 내용 및 평점 수정
	public int updateReview(ReviewRequestDto reviewRequestDto);
	
	
	// 새로운 이미지 추가
	 int insertReviewImages(@Param("reviewNo") int reviewNo, @Param("reviewImages") List<FileRequestDto> reviewImages);
	 
	 
	 
	
	public List<ReviewResponseDto> getFileInfoByReviewNo(@Param("reviewNo")int reviewNo);
	public void deleteTag(@Param("reviewNo")int reviewNo);
	public void deleteReviewImages(@Param("reviewNo")int reviewNo);
	public int deleteReview(@Param("reviewNo")int reviewNo);
	
	
	List<ReviewResponseDto> getTagCounts(@Param("bakeryNo")double bakeryNo);
	
	
	
	
	
}

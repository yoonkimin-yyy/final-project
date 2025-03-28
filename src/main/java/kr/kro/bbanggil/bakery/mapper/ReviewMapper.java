package kr.kro.bbanggil.bakery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.bakery.dto.request.FileRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.ReviewRequestDto;
import kr.kro.bbanggil.bakery.dto.response.PageResponseDto;
import kr.kro.bbanggil.bakery.dto.response.ReviewResponseDto;

@Mapper
public interface ReviewMapper {

	
	public void insertReview(ReviewRequestDto reviewDto);
	public int checkOrderExists(@Param("orderNo") int orderNo);
	public Integer getLatestBakeryNo();
	public int findCartNoByUserNo(@Param("userNo") int userNo);
	public int findOrderNobyCartNo(@Param("cartNo") int cartNo);
	public void insertReviewImage(FileRequestDTO fileRequestDto);
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
	 int insertReviewImages(@Param("reviewNo") int reviewNo, @Param("reviewImages") List<FileRequestDTO> reviewImages);
	 
	 
	 
	
	public List<ReviewResponseDto> getFileInfoByReviewNo(@Param("reviewNo")int reviewNo);
	public void deleteTag(@Param("reviewNo")int reviewNo);
	public void deleteReviewImages(@Param("reviewNo")int reviewNo);
	public int deleteReview(@Param("reviewNo")int reviewNo);
	
	
	List<ReviewResponseDto> getTagCounts(@Param("bakeryNo")double bakeryNo);
	
	
	// 세션에 있는 userNo로 bakeryNo 찾기
	public List<Integer> getBakeryNoByUserNo(int userNo);
	
	// 리뷰 답글 삽입
    public void insertReviewReply(@Param("reviewNo") int reviewNo,
                           @Param("userNo") int userNo,
                           @Param("reviewReply") String reviewReply
                           );
    
    
    // bakeryNo로 리뷰 답글을 가져오는 메소드
    List<ReviewResponseDto> selectReviewRepliesByBakeryNo(@Param("bakeryNo")int bakeryNo);
    
    List<ReviewResponseDto> reviewCheck(@Param("bakeryNo") int bakeryNo );
    
    void reviewReport(@Param("reviewNo")int reviewNo, @Param("reviewReportDetail")String reviewReportDetail, @Param("userNo") int userNo);
	
    public void updateReviewTags(
    	    @Param("reviewNo") int reviewNo,
    	    @Param("tagFirst") String tagFirst,
    	    @Param("tagSecond") String tagSecond,
    	    @Param("tagThird") String tagThird,
    	    @Param("tagForth") String tagForth,
    	    @Param("tagFive") String tagFive
    	);
}

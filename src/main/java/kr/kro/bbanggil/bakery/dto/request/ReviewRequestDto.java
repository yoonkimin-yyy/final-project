package kr.kro.bbanggil.bakery.dto.request;

import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class ReviewRequestDto {
	private int reviewNo;
    private int userNo;  // 리뷰 작성자 no
    private int orderNo=2;
    private int tagNo; 
    private double reviewRating;     // 리뷰 평점 (0.5 ~ 5.0)
    private String reviewDetail;     // 리뷰 내용
    private List<String> reviewTag;  //  태그 리스트 그대로 유지
    private List<MultipartFile> reviewImage; // 업로드할 리뷰 이미지 파일
    private double bakeryNo;            // 빵집 번호 
    private String writeDate;        //  날짜 필드 
    private String reviewImageName; // 저장된 파일명 추가
    private String changeName;
	private String deleteImages;
    
    private String tagFirst;
    private String tagSecond;
    private String tagThird;
    private String tagForth;
    private String tagFive;
    
    // 사장님 답글
    private String reviewReply;
    
    
    
}
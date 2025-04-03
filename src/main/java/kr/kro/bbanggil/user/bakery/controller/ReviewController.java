package kr.kro.bbanggil.user.bakery.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import kr.kro.bbanggil.global.exception.BbanggilException;
import kr.kro.bbanggil.user.bakery.dto.request.ReviewRequestDto;
import kr.kro.bbanggil.user.bakery.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

	private static final Logger logger = LogManager.getLogger(ReviewController.class);
	private final ReviewServiceImpl reviewService;

	/*
	 * 리뷰 작성 insert
	 */
	@PostMapping("/write")
	public ResponseEntity<String> writeReview(@RequestPart("reviewDto") ReviewRequestDto reviewDto, // JSON 데이터 받기
			@RequestPart(value = "reviewImage", required = false) List<MultipartFile> reviewImage, // 이미지 받기
	HttpSession session) {
		
	
		
		 try {
		        String userId = (String) session.getAttribute("userId");
		        Integer userNo = (Integer) session.getAttribute("userNum");

		        reviewDto.setUserNo(userNo);
		        reviewDto.setUserId(userId);
		        reviewDto.setReviewImage(reviewImage);

		        reviewService.writeReview(reviewDto); // 리뷰 저장

		        return ResponseEntity.ok("리뷰 등록 완료");

		    } catch (IllegalStateException | IllegalArgumentException e) {
		        // 
		        return ResponseEntity.badRequest().body(e.getMessage());
		    } catch (Exception e) {
		        e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		                             .body("리뷰 작성 중 알 수 없는 오류가 발생했습니다.");
		    }

	}

	@PostMapping("/edit")
	public String editReview(@ModelAttribute ReviewRequestDto reviewRequestDto,
			@RequestParam(value = "reviewImage", required = false) List<MultipartFile> files
			) {

		int result = reviewService.editReview(reviewRequestDto, files);
		System.out.println(result);
		

		// bakeryNo
		return "redirect:/bakery/detail?bakeryNo=" + reviewRequestDto.getBakeryNo();

	}

	@PostMapping("/delete")
	public ResponseEntity<String> deleteReview(@RequestParam("reviewNo") int reviewNo,
			@RequestParam(value = "fileName", defaultValue = "none") String fileName,
			HttpSession session) {
		
		Integer userNo = (Integer) session.getAttribute("userNum");
		System.out.println(fileName);
		
	    if (userNo == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
	    }
		
		
		int result = reviewService.deleteReview(reviewNo, userNo, fileName);
		
		if (result > 0) {
			return ResponseEntity.ok("리뷰 삭제 성공");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리뷰 삭제 실패");
		}

	}
	
	// 사장님 리뷰 답글
	@PostMapping("/reply")
	public String reiewReply(@RequestParam("reviewNo") int reviewNo,
								@RequestParam("bakeryNo") Double bakeryNoDouble,
								@RequestParam("replyText") String reviewReply,
								@SessionAttribute("userNum") int userNo,
								HttpSession session,
								Model model) {
		
		// 빵집 번호를 int로 변경
		int bakeryNo = bakeryNoDouble.intValue();
		session.setAttribute("userNum", userNo);
		
		

		// 서비스에 답글 등록 요청
		reviewService.insertReply(reviewNo, bakeryNo, reviewReply,userNo);

		return "redirect:/bakery/detail?bakeryNo=" + bakeryNo;
	}
	
	@GetMapping("/report/form")
	public String reviewReportForm(@RequestParam("reviewNo") int reviewNo,
									HttpSession session,
									Model model) {
		if(session.getAttribute("userNum") == null) {
			throw new BbanggilException("로그인 후 신고가 가능합니다.","common/error",HttpStatus.BAD_REQUEST);
		} else {
			model.addAttribute("reviewNo", reviewNo);
			
			return "user/review-report";
		}
	}
	
	@PostMapping("/report")
	public String reviewReport(@RequestParam("reviewNo") int reviewNo,
					@SessionAttribute(name = "userNum", required = false) int userNo,
					@ModelAttribute ReviewRequestDto requestDTO,
								HttpSession session,Model model) {
		model.addAttribute("reviewNo", reviewNo);
		
		
		
		
		reviewService.reviewReport(requestDTO,userNo);
		
		
		return "user/review-report";
	}
		
	
	
}

package kr.kro.bbanggil.user.bakery.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.kro.bbanggil.common.util.AwsS3Util;
import kr.kro.bbanggil.common.util.FileUploadUtil;
import kr.kro.bbanggil.global.exception.BbanggilException;
import kr.kro.bbanggil.owner.order.service.OrderServiceImpl;
import kr.kro.bbanggil.user.bakery.dto.request.FileRequestDTO;
import kr.kro.bbanggil.user.bakery.dto.request.ReviewRequestDto;
import kr.kro.bbanggil.user.bakery.dto.response.PageResponseDto;
import kr.kro.bbanggil.user.bakery.dto.response.ReviewResponseDto;
import kr.kro.bbanggil.user.bakery.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);
	private final ReviewMapper reviewMapper;
	private final FileUploadUtil fileUploadUtil;

	private final AwsS3Util util;
	private final OrderServiceImpl orderSerivce;

	/*
	 * 리뷰 작성후 insert service
	 */
	public void writeReview(ReviewRequestDto reviewDto) {
		try {
			logger.info(" 리뷰 작성 시작 - userId: {}, bakeryNo: {}, orderNo: {}", reviewDto.getUserNo(),
					reviewDto.getBakeryNo(), reviewDto.getOrderNo());

			if (reviewDto.getUserNo() == null || reviewDto.getOrderNo() == null) {
				logger.error("userNo 또는 orderNo가 null입니다. userNo: {}, orderNo: {}", reviewDto.getUserNo(),
						reviewDto.getOrderNo());
				throw new IllegalArgumentException("사용자 번호 또는 주문 번호가 누락되었습니다.");
			}

			// ORDER_NO가 존재하는지 확인
			int orderExists = reviewMapper.checkOrderExists(reviewDto.getOrderNo());

			if (orderExists == 0) {
				throw new IllegalArgumentException(" 존재하지 않는 ORDER_NO: " + reviewDto.getOrderNo());
			}

			// 주문한 사용자인지 확인하는 추가 검증
			boolean isOrederValid = orderSerivce.isUserOrder(reviewDto.getUserNo(), reviewDto.getOrderNo());

			if (!isOrederValid) {
				throw new IllegalArgumentException("이 주문은 해당 사용자에게 속하지 않습니다.");
			}

			// 이미 리뷰를 작성했는지 확인
			int reviewExists = reviewMapper.checkReviewExistsByOrderNo(reviewDto.getOrderNo());
			logger.info("중복 리뷰 존재 여부 (orderNo={}): {}", reviewDto.getOrderNo(), reviewExists);
			if (reviewExists > 0) {
				throw new IllegalStateException("이미 이 주문에 대한 리뷰가 존재합니다.");
			}

			// 1. 리뷰 INSERT (먼저 리뷰 데이터 저장)
			reviewMapper.insertReview(reviewDto);
			logger.info(" 리뷰 저장 완료 - reviewNo: {}", reviewDto.getReviewNo());

			// 2. 이미지 업로드 처리
			List<MultipartFile> files = reviewDto.getReviewImage();
			if (files != null && !files.isEmpty()) {
				for (MultipartFile file : files) {
					if (!file.isEmpty()) {
						logger.info(" 이미지 업로드 시작 - 원본 파일명: {}", file.getOriginalFilename());

						FileRequestDTO fileRequestDto = new FileRequestDTO();
						fileRequestDto.setReviewNo(reviewDto.getReviewNo());
						System.out.println(fileRequestDto.getReviewNo());

						util.saveFile(file, fileRequestDto);

						// 리뷰이미지 정보 리뷰 이미지 테이블에 저장
						reviewMapper.insertReviewImage(fileRequestDto);

						logger.info(" 이미지 업로드 완료 - 저장된 파일명: {}", fileRequestDto.getChangeName());
					}
				}
			}
			if (reviewDto.getReviewTag() != null && !reviewDto.getReviewTag().isEmpty()) {
				ReviewRequestDto reviewTagDto = new ReviewRequestDto();

				// 리뷰 번호 설정
				reviewTagDto.setReviewNo(reviewDto.getReviewNo());

				// 태그 번호 설정 (MyBatis에서 자동 증가하는 시퀀스를 사용하면 필요 없음)
				reviewTagDto.setTagNo(reviewDto.getTagNo());

				// 최대 5개 태그 설정 (없으면 NULL로 유지)
				List<String> tags = reviewDto.getReviewTag();
				reviewTagDto.setTagFirst(tags.size() > 0 ? tags.get(0) : null);
				reviewTagDto.setTagSecond(tags.size() > 1 ? tags.get(1) : null);
				reviewTagDto.setTagThird(tags.size() > 2 ? tags.get(2) : null);
				reviewTagDto.setTagForth(tags.size() > 3 ? tags.get(3) : null);
				reviewTagDto.setTagFive(tags.size() > 4 ? tags.get(4) : null);

				// 태그 저장
				reviewMapper.insertReviewTag(reviewTagDto);

				logger.info("  리뷰 태그 저장 완료 - reviewNo: {}", reviewDto.getReviewNo());
			}

		} catch (IOException e) {
			logger.error(" 파일 업로드 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("파일 업로드 중 오류 발생!", e);
		} catch (Exception e) {
			logger.error(" 리뷰 저장 중 오류 발생: {}", e.getMessage(), e);
			throw new RuntimeException("리뷰 저장 중 오류 발생?", e);
		}
	}

	@Override
	public int getTotalReviewCount(double no) {

		return reviewMapper.getTotalCount(no);
	}

	@Override
	public Map<String, Object> list(PageResponseDto pageInfo, int currentPage, int postCount, int pageLimit,
			int boardLimit, double bakeryNo, String sort) {

		String orderBy = "r.review_date DESC";

		if ("highest".equals(sort)) {
			orderBy = "r.review_rating DESC";
		} else if ("lowest".equals(sort)) {
			orderBy = "r.review_rating ASC";
		}

		List<ReviewResponseDto> reviews = reviewMapper.list(pageInfo, bakeryNo, orderBy);

		Map<String, Object> result = new HashMap<>();
		result.put("pi", pageInfo);
		result.put("reviews", reviews);

		return result;

	}

	@Override
	public List<ReviewResponseDto> getReviewImages(double bakeryNo) {

		List<ReviewResponseDto> reviewImages = reviewMapper.getReviewImages(bakeryNo);

		return reviewMapper.getReviewImages(bakeryNo);

	}

	@Override
	public ReviewResponseDto getReviewId(int reviewNo) {
		
		
		 
		return reviewMapper.getReviewById(reviewNo);
	}

	@Override
	public int editReview(ReviewRequestDto reviewRequestDto, List<MultipartFile> files) {
		ReviewResponseDto existingReview = reviewMapper.getReviewById(reviewRequestDto.getReviewNo());

		if (existingReview == null) {
			return 0; // 리뷰가 존재하지 않으면 수정할 수 없음
		}

		// 1. 리뷰 내용 및 평점 업데이트

		reviewMapper.updateReview(reviewRequestDto);

		List<String> tagList = reviewRequestDto.getReviewTag();

		String tagFirst = null, tagSecond = null, tagThird = null, tagForth = null, tagFive = null;

		if (tagList != null) {
			if (tagList.size() > 0)
				tagFirst = tagList.get(0);
			if (tagList.size() > 1)
				tagSecond = tagList.get(1);
			if (tagList.size() > 2)
				tagThird = tagList.get(2);
			if (tagList.size() > 3)
				tagForth = tagList.get(3);
			if (tagList.size() > 4)
				tagFive = tagList.get(4);
		}


		reviewMapper.updateReviewTags(reviewRequestDto.getReviewNo(), tagFirst, tagSecond, tagThird, tagForth, tagFive);

		// 2. 새로운 이미지 업로드 처리 (추가된 이미지만 `review_img` 테이블에 저장)
		List<FileRequestDTO> savedFileList = new ArrayList<>();
		if (files != null && !files.isEmpty()) {
			for (MultipartFile file : files) {
				FileRequestDTO fileRequestDto = new FileRequestDTO();
				try {
					util.saveFile(file, fileRequestDto);
				} catch (IOException e) {
					e.printStackTrace();
					continue; // 파일 하나 업로드 실패해도 다른 파일 업로드 계속 진행
				}
				savedFileList.add(fileRequestDto);
			}

			// DB에 새로운 이미지 정보 저장
			if (!savedFileList.isEmpty()) {
				reviewMapper.insertReviewImages(reviewRequestDto.getReviewNo(), savedFileList);
			}
		}

		return 1; // 성공적으로 리뷰 수정됨

	}

	@Override
	public int deleteReview(int reviewNo, int userNo, String fileName) {

		logger.info("리뷰 삭제 요청 - reviewNo: {}, userNo: {}, fileName: {}", reviewNo, userNo, fileName);

		ReviewResponseDto review = reviewMapper.getReviewById(reviewNo);
		

		if (review == null || !Objects.equals(review.getUserNo(), userNo)) {
			logger.warn("리뷰가 없거나 작성자가 아닙니다. reviewNo: {}, 요청한 userNo: {}, 실제 userNo: {}", reviewNo, userNo,
					review != null ? review.getUserNo() : "null");
			return 0;
		}

		// 파일 삭제
		List<ReviewResponseDto> fileDataList = reviewMapper.getFileInfoByReviewNo(reviewNo);
		if (fileName != null && !fileName.equals("none")) {
			for (ReviewResponseDto fileData : fileDataList) {
				if (fileData.getLocalPath() != null) {
					logger.info("이미지 삭제 시도 - changeName: {}", fileData.getChangeName());
					util.deleteImage(fileData.getChangeName());
					logger.info("이미지 삭제 완료 - changeName: {}", fileData.getChangeName());
				} else {
					logger.warn("이미지 정보에 localPath가 없음 - fileData: {}", fileData);
				}
			}
		} else {
			logger.info("파일 없이 리뷰 삭제 진행");
		}

		// 태그 삭제
		reviewMapper.deleteTag(reviewNo);
		logger.info("리뷰 태그 삭제 완료 - reviewNo: {}", reviewNo);

		// 이미지 DB 삭제
		reviewMapper.deleteReviewImages(reviewNo);
		logger.info("리뷰 이미지 DB 삭제 완료 - reviewNo: {}", reviewNo);

		// 리뷰 삭제
		int result = reviewMapper.deleteReview(reviewNo);
		if (result > 0) {
			logger.info("리뷰 삭제 완료 - reviewNo: {}, 삭제 결과: {}", reviewNo, result);
		} else {
			logger.error("리뷰 삭제 실패 - reviewNo: {}", reviewNo);
		}

		return result;

	}

	@Override
	public Map<String, Integer> getTagCounts(double bakeryNo) {

		List<ReviewResponseDto> tagList = reviewMapper.getTagCounts(bakeryNo);

		System.out.println(tagList);
		Map<String, Integer> tagCounts = new HashMap<>();

		for (ReviewResponseDto tag : tagList) {
			addTagToMap(tagCounts, tag.getTagFirst());
			addTagToMap(tagCounts, tag.getTagSecond());
			addTagToMap(tagCounts, tag.getTagThird());
			addTagToMap(tagCounts, tag.getTagForth());
			addTagToMap(tagCounts, tag.getTagFive());
		}
		System.out.println(tagCounts);
		return tagCounts;

	}

	/*
	 * 태그 개수를 계산하는 헬퍼 메서드
	 */
	private void addTagToMap(Map<String, Integer> tagCounts, String tag) {
		if (tag != null && !tag.isEmpty()) {
			tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);
		}
	}

	// 리뷰 답글 등록
	@Override
	public int[] insertReply(int reviewNo, int bakeryNo, String reviewReply, int userNo) {
		List<Integer> result = reviewMapper.getBakeryNoByUserNo(userNo);
		if (result.isEmpty()) {
			throw new BbanggilException("소유한 가게가 없습니다.", "common/error", HttpStatus.BAD_REQUEST);
		}

		int[] bakeryNosArray = result.stream().mapToInt(Integer::intValue).toArray();

		boolean containsBakeryNo = false;
		for (int n : bakeryNosArray) {
			if (n == bakeryNo) {
				containsBakeryNo = true;
				break;
			}
		}

		if (containsBakeryNo) {
			// bakeryNosArray에 bakeryNo가 포함되어 있을 경우 실행
			reviewMapper.insertReviewReply(reviewNo, userNo, reviewReply);
			return bakeryNosArray;

		}

		throw new BbanggilException("본인의 가게가 아닙니다.", "common/error", HttpStatus.BAD_REQUEST);

	}

	// bakeryNo에 해당하는 리뷰 답글 목록을 가져오는 메소드
	@Override
	public List<ReviewResponseDto> getReviewReplies(Double bakeryNo) {

		int bakeryNoInt = bakeryNo.intValue();
		return reviewMapper.selectReviewRepliesByBakeryNo(bakeryNoInt);
	}

	// 로그인 한 사용자가 해당 빵집을 소유하고 있는지
	@Override
	public int byIdCheck(int userNo, double bakeryNo) {
		// 사용자에 대한 bakery 번호 목록 가져오기
		List<Integer> result = reviewMapper.getBakeryNoByUserNo(userNo);

		int bakeryNoInt = (int) bakeryNo;

		int resultValue;
		// List<Integer>를 int[]로 변환
		int[] bakeryNoArray = result.stream().mapToInt(Integer::intValue).toArray();

		// bakeryNo가 bakeryNoArray에 포함되어 있는지 확인
		boolean containsBakeryNo = false;
		for (int n : bakeryNoArray) {
			if (n == bakeryNoInt) {
				containsBakeryNo = true;
				break;
			}
		}

		if (containsBakeryNo) {
			resultValue = bakeryNoInt;
			return resultValue;
		}

		return 0;
	}

	@Override
	public List<Integer> reviewCheck(int bakeryNo) {
		List<ReviewResponseDto> reviewList = reviewMapper.reviewCheck(bakeryNo); // reviewMapper에서 ReviewResponseDto 객체
																					// 목록을 반환
		List<Integer> reviewNoList = new ArrayList<>();

		for (ReviewResponseDto review : reviewList) {
			reviewNoList.add(review.getReviewNo()); // ReviewResponseDto에서 review_no를 추출해서 Integer 목록에 추가
		}
		return reviewNoList;
	}

	@Override
	public void reviewReport(ReviewRequestDto reviewDTO, int userNo) {

		int reviewNo = reviewDTO.getReviewNo();
		String reviewReportDetail = reviewDTO.getReviewReportDetail();
		reviewMapper.reviewReport(reviewNo, reviewReportDetail, userNo);
	}

}
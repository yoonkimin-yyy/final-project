package kr.kro.bbanggil.bakery.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.kro.bbanggil.bakery.dto.request.FileRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.ReviewRequestDto;
import kr.kro.bbanggil.bakery.dto.response.PageResponseDto;
import kr.kro.bbanggil.bakery.dto.response.ReviewResponseDto;
import kr.kro.bbanggil.bakery.mapper.ReviewMapper;
import kr.kro.bbanggil.common.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);
	private final ReviewMapper reviewMapper;
	private final FileUploadUtil fileUploadUtil;

	/*
	 * 리뷰 작성후 insert service
	 */
	public void writeReview(ReviewRequestDto reviewDto) {
		try {
			logger.info(" 리뷰 작성 시작 - userId: {}, bakeryNo: {}, orderNo: {}", reviewDto.getUserNo(),
					reviewDto.getBakeryNo(), reviewDto.getOrderNo());

			// ORDER_NO가 존재하는지 확인
			int orderExists = reviewMapper.checkOrderExists(reviewDto.getOrderNo());

			if (orderExists == 0) {
				throw new IllegalArgumentException(" 존재하지 않는 ORDER_NO: " + reviewDto.getOrderNo());
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
						fileUploadUtil.uploadFile(file, fileRequestDto, "bakery"); // 개별 파일 업로드

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
		System.out.println(orderBy);
		System.out.println("fsfsfsf");

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

		System.out.println();
		if (existingReview == null) {
			return 0; // 리뷰가 존재하지 않으면 수정할 수 없음
		}

		// 1. 리뷰 내용 및 평점 업데이트

		System.out.println(reviewRequestDto.getReviewDetail());
		System.out.println(reviewRequestDto.getReviewNo());
		System.out.println(reviewRequestDto.getReviewRating());
		reviewMapper.updateReview(reviewRequestDto);

		// 2. 새로운 이미지 업로드 처리 (추가된 이미지만 `review_img` 테이블에 저장)
		List<FileRequestDTO> savedFileList = new ArrayList<>();
		if (files != null && !files.isEmpty()) {
			for (MultipartFile file : files) {
				FileRequestDTO fileRequestDto = new FileRequestDTO();
				try {
					fileUploadUtil.uploadFile(file, fileRequestDto, "bakery");
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

		List<ReviewResponseDto> fileDataList = reviewMapper.getFileInfoByReviewNo(reviewNo);

		System.out.println(fileDataList);

		if (fileDataList.isEmpty() || fileDataList.get(0).getUserNo() != userNo) {
			System.out.println("파일 데이터가 없거나 사용자 번호 불일치!");
			return 0;
		}
		try {
			// 파일이 있을 경우 삭제 (여러 개의 파일 가능)
			if (fileName != null && !fileName.equals("none")) {
				for (ReviewResponseDto fileData : fileDataList) {
					if (fileData.getLocalPath() != null) {
						String localPath = fileData.getLocalPath();
						fileUploadUtil.deleteFile(localPath, "bakery", fileName);
					}
				}
			} else {
				System.out.println(" 파일 없이 리뷰 삭제 진행");
			}

			// 태그 삭제
			reviewMapper.deleteTag(reviewNo);
			System.out.println(" 태그 삭제 완료");

			// 이미지 삭제
			reviewMapper.deleteReviewImages(reviewNo);
			System.out.println(" 이미지 삭제 완료");

			// 리뷰 삭제
			int result = reviewMapper.deleteReview(reviewNo);
			System.out.println(" 리뷰 삭제 완료 - 결과: " + result);

			return result;

		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}

	}

	@Override
	public Map<String, Integer> getTagCounts(double bakeryNo) {

		System.out.println("rlals");
		List<ReviewResponseDto> tagList = reviewMapper.getTagCounts(bakeryNo);

		System.out.println(tagList);
		System.out.println("dfsfsfsfs");
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

}
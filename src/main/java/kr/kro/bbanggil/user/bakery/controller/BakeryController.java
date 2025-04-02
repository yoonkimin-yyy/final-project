package kr.kro.bbanggil.user.bakery.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.kro.bbanggil.admin.dto.response.myBakeryResponseDTO;
import kr.kro.bbanggil.common.dto.PageInfoDTO;
import kr.kro.bbanggil.common.util.PaginationUtil;
import kr.kro.bbanggil.owner.order.service.OrderService;
import kr.kro.bbanggil.user.bakery.dto.BakeryDto;
import kr.kro.bbanggil.user.bakery.dto.BakeryImageDTO;
import kr.kro.bbanggil.user.bakery.dto.BakeryInfoDTO;
import kr.kro.bbanggil.user.bakery.dto.BakerySearchDTO;
import kr.kro.bbanggil.user.bakery.dto.request.BakeryImgRequestDTO;
import kr.kro.bbanggil.user.bakery.dto.request.BakeryRequestDTO;
import kr.kro.bbanggil.user.bakery.dto.request.MenuDetailRequestDto;
import kr.kro.bbanggil.user.bakery.dto.response.BakeryResponseDto;
import kr.kro.bbanggil.user.bakery.dto.response.MenuResponseDto;
import kr.kro.bbanggil.user.bakery.dto.response.ReviewResponseDto;
import kr.kro.bbanggil.user.bakery.dto.response.bakeryUpdateResponseDTO;
import kr.kro.bbanggil.user.bakery.service.BakeryService;
import kr.kro.bbanggil.user.bakery.service.ReviewService;
import kr.kro.bbanggil.user.bakery.util.ListPageNation;
import kr.kro.bbanggil.user.member.dto.response.OwnerInfoResponseDTO;
import kr.kro.bbanggil.user.member.service.MypageService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/bakery")
@AllArgsConstructor
public class BakeryController {

	private final BakeryService bakeryService;
	private final MypageService mypageService;
	private final ReviewService reviewService;
	private final OrderService orderService;
	private final PaginationUtil pageNation;

	@GetMapping("/list")
	public String list(@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(value = "orderType", required = false, defaultValue = "recent") String orderType,
			@ModelAttribute BakerySearchDTO bakerySearchDTO, Model model,
			@SessionAttribute(value="userNum",required = false)Integer userNo) {
		// 전체 게시물
		int postCount = bakeryService.totalCount(bakerySearchDTO);
		int pageLimit = 5;
		int boardLimit = 10;

		if(userNo == null) {
			userNo = 0;
		}
		Map<String, Object> result = bakeryService.bakeryList(pageNation, currentPage, postCount, pageLimit, boardLimit,
				orderType, bakerySearchDTO,userNo);

		PageInfoDTO piResult = (PageInfoDTO) result.get("pi");

		List<BakeryInfoDTO> postsResult = (List<BakeryInfoDTO>) result.get("posts");
		List<List<BakeryInfoDTO>> imagesResult = (List<List<BakeryInfoDTO>>) result.get("images");
		String todayDayOfWeek = (String) result.get("today");
		List<BakeryInfoDTO> bakeryInfo = new ArrayList<>();
		String locationAgree = (String) result.get("locationAgree");

		for (int i = 0; i < postsResult.size(); i++) {
			BakeryInfoDTO post = postsResult.get(i);
			for (int j = 0; j < imagesResult.get(i).size(); j++) {
				post.setBakeryImageDTO(imagesResult.get(i).get(j).getBakeryImageDTO());
			}
			bakeryInfo.add(post);
		}

    model.addAttribute("orderType", orderType);
		model.addAttribute("posts", bakeryInfo);
		model.addAttribute("pi", piResult);
		model.addAttribute("today", todayDayOfWeek);
		model.addAttribute("bakerySearchDTO", bakerySearchDTO);
		model.addAttribute("searchText", bakerySearchDTO.getSearchText());
		model.addAttribute("locationAgree", locationAgree);

		return "user/bakery-list";
	}

	@GetMapping("/insert/form")
	public String bakeryInsertForm(BakeryRequestDTO BakeryRequestDTO, Model model) {
		model.addAttribute(BakeryRequestDTO);
		model.addAttribute("closeWindow", true);
		return "owner/bakery-insert";
	}

	/**
	 * 
	 * @param BakeryRequestDTO    : insert에 대한 전반적인 데이터가 들어있는 DTO
	 * @param BakeryImgRequestDTO : insert에 필요한 이미지들을 포함하는 DTO dateSet() : weekday,
	 *                            weekend 데이터 입력 시 각 요일에 맞게 데이터를 넣어주는 메서드 timeSet()
	 *                            : 각 요일에 opentime, closetime를 설정해주는 메서드
	 */
	@PostMapping("/insert")
	public String bakeryInsert(@ModelAttribute @Valid BakeryRequestDTO BakeryRequestDTO,
			@ModelAttribute BakeryImgRequestDTO BakeryImgRequestDTO, @SessionAttribute("userNum") int userNo,
			@SessionAttribute("role") String role, Model model) throws Exception {
		BakeryRequestDTO.setTime();
		int bakeryNo = bakeryService.bakeryInsert(BakeryRequestDTO, BakeryImgRequestDTO, userNo, role);
		model.addAttribute("no", bakeryNo);
		return "redirect:/bakery/menu/list/form?no=" + bakeryNo;
	}

	@GetMapping("/detail")
	public String getBakeryImages(@RequestParam(value = "bakeryNo", required = false) double no,
			@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(value = "sort", defaultValue = "latest") String sort, Model model, HttpSession session) {
		{
			bakeryService.updateUserCount((int) no);

			/*
			 * 세션에서 userNum 가져오기
			 */
			Integer userNum = (Integer) session.getAttribute("userNum");

			/**
			 * 가게 정보 가져오는 기능
			 */
			List<BakeryDto> bakeriesInfo = bakeryService.getBakeryImages(no);
			model.addAttribute("bakeriesInfo", bakeriesInfo);

			List<BakeryDto> getBakeriesInfo = bakeryService.getBakeriesInfo(no);
			model.addAttribute("getBakeriesInfo", getBakeriesInfo);

			List<BakeryDto> getBakeriesTime = bakeryService.getBakeriesTime(no);
			model.addAttribute("getBakeriesTime", getBakeriesTime);

			/*
			 * / 빵집 번호로 사장님 답글 가져오기
			 */
			List<ReviewResponseDto> reviewReplies = reviewService.getReviewReplies(no);
			if (reviewReplies.size() < 10) {
				ReviewResponseDto dto = new ReviewResponseDto();
				dto.setReviewNo(0);
				reviewReplies.add(dto);
			}
			model.addAttribute("reviewReplies", reviewReplies);

			/*
			 * / 로그인 한 사용자가 빵집 가게를 소유하고 있는지
			 */
			if (userNum != null) {
				int userNo = (int) userNum;
				int resultValue = reviewService.byIdCheck(userNo, no);
				if (resultValue == 0) {
					int bakeryNoInt = (int) no;
					model.addAttribute("bakeryNoUrlValue", bakeryNoInt);
					model.addAttribute("bakeryNoValue", resultValue);
				} else {
					int bakeryNoInt = (int) no;
					List<Integer> reviewCheck = reviewService.reviewCheck(bakeryNoInt);
					int[] reviewCheckArray = reviewCheck.stream().mapToInt(Integer::intValue).toArray();

					List<Integer> reviewNoCheck = Arrays.stream(reviewCheckArray).boxed() // int[] → List<Integer> 변환
							.collect(Collectors.toList());

					model.addAttribute("reviewNoCheck", reviewNoCheck);
					model.addAttribute("bakeryNoUrlValue", bakeryNoInt);
					model.addAttribute("bakeryNoValue", resultValue);
					model.addAttribute("reviewCheck", reviewCheck);
				}
			}

			/*
			 * / 리뷰 리스트 pagination
			 */
			int pageLimit = 5;
			int reviewLimit = 10;

			int totalReviews = reviewService.getTotalReviewCount(no);

			PageInfoDTO pageInfo = PaginationUtil.getPageInfo(totalReviews, currentPage, pageLimit, reviewLimit);

			Map<String, Object> result = reviewService.list(pageInfo, currentPage, totalReviews, pageLimit, reviewLimit,
					no, sort);

			model.addAttribute("pi", pageInfo);
			model.addAttribute("reviews", result.get("reviews"));
			model.addAttribute("bakeryNo", no);
			model.addAttribute("sort", sort);
			model.addAttribute("totalReviews", totalReviews);

			/**
			 * 메뉴 리스트 보여주는 기능
			 */
			List<MenuResponseDto> menuList = bakeryService.getMenuInfo(no);
			model.addAttribute("menuList", menuList);

			/*
			 * 편의정보, 실내사진, 외부 사진 보여지는 기능
			 */

			List<BakeryDto> bakeryDetail = bakeryService.getBakeryDetail(no);
			List<BakeryImageDTO> insideImages = bakeryService.getInsideImages(no);
			List<BakeryImageDTO> outsideImages = bakeryService.getOutsideImages(no);
			List<BakeryImageDTO> parkingImages = bakeryService.getParkingImages(no);

			model.addAttribute("bakeryDetail", bakeryDetail);
			model.addAttribute("insideImages", insideImages);
			model.addAttribute("outsideImages", outsideImages);
			model.addAttribute("parkingImages", parkingImages);

			/*
			 * 리뷰 이미지 보여지는 기능
			 */
			List<ReviewResponseDto> reviewImages = reviewService.getReviewImages(no);
			model.addAttribute("reviewImages", reviewImages);

			Map<String, Integer> tagCounts = reviewService.getTagCounts(no);

			model.addAttribute("tagCounts", tagCounts);
			model.addAttribute("bakeryNo", no);

			model.addAttribute("userNum", userNum);
			
			
			
			

			/*
			 * 사용자의 orderNo가져오기
			 */

			if (userNum != null) {
				BakeryResponseDto recentOrder = orderService.findOrderNo(userNum, no);

				if (recentOrder != null) {
					model.addAttribute("recentOrder", recentOrder);
				} else {
				}
			}


			return "user/bakery-detail"; // bakeryDetail.html 뷰 반환

		}

	}

	@PostMapping("/cart/add")
	public String addCart(HttpSession session, @RequestParam("orderData") String orderData) {

		Integer userNo = (Integer) session.getAttribute("userNum");

		ObjectMapper objectMapper = new ObjectMapper();

		List<MenuDetailRequestDto> menuDtoList = new ArrayList<>();

		try {
			/*
			 * / 배열로 먼저 파싱하고 리스트로 변환
			 */
			MenuDetailRequestDto[] dtoArray = objectMapper.readValue(orderData, MenuDetailRequestDto[].class);
			menuDtoList = Arrays.asList(dtoArray);

		} catch (Exception e) {
			e.printStackTrace(); // 파싱 에러 로그
		}
		bakeryService.addCart(userNo, menuDtoList);

		return "redirect:/order/page";
	}

	@GetMapping("/kakao")

	public ResponseEntity<BakeryDto> getKakaoMap(@RequestParam("bakeryNo") double bakeryNo) {

		BakeryDto bakery = bakeryService.getBakeryByNo(bakeryNo);

		if (bakery == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.ok(bakery);
	}

	@GetMapping("/detail/{bakeryNo}")
	public String getBakeryImages(@PathVariable("bakeryNo") double no, Model model) {

		/**
		 * 가게 정보 가져오는 기능
		 */
		List<BakeryDto> bakeriesInfo = bakeryService.getBakeryImages(no);
		model.addAttribute("bakeriesInfo", bakeriesInfo);

		return "user/bakery-detail"; // bakeryDetail.html 뷰 반환
	}

	@GetMapping("/update/form")
	public String bakeryUpdateForm(@RequestParam(name = "bakeryNo", required = false) Integer bakeryNo, Model model) {
		bakeryUpdateResponseDTO result = bakeryService.getbakeryInfo(bakeryNo);
		model.addAttribute("bakery", result);
		return "owner/bakery-update";
	}

	@PostMapping("/update")
	public String bakeryUpdate(BakeryRequestDTO bakeryRequestDTO, BakeryImgRequestDTO bakeryImgRequestDTO,
			@SessionAttribute("userNum") int userNo) {
		bakeryService.bakeryUpdate(bakeryRequestDTO, bakeryImgRequestDTO, userNo);
		return "redirect:/mypage/owner";
	}
	
	@GetMapping("/info/form")
	public String bakeryInfoForm(@RequestParam("bakeryNo") int bakeryNo, @SessionAttribute("userNum") int userNum,
			Model model) {
		myBakeryResponseDTO result = bakeryService.bakeryInfo(bakeryNo);
		OwnerInfoResponseDTO info = mypageService.ownerInfo(userNum);
		model.addAttribute("info", info);
		model.addAttribute("bakery", result);
		model.addAttribute("no", bakeryNo);
		model.addAttribute("goMyPage",true);
		return "/owner/bakery-info";
	}

}

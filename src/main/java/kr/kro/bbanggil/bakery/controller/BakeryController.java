package kr.kro.bbanggil.bakery.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import kr.kro.bbanggil.bakery.dto.BakeryDto;
import kr.kro.bbanggil.bakery.request.dto.MenuRequestDto;
import kr.kro.bbanggil.bakery.response.dto.MenuResponseDto;
import kr.kro.bbanggil.bakery.review.response.dto.PageResponseDto;
import kr.kro.bbanggil.bakery.review.response.dto.ReviewResponseDto;
import kr.kro.bbanggil.bakery.service.BakeryServiceImpl;
import kr.kro.bbanggil.bakery.service.ReviewServiceImpl;
import kr.kro.bbanggil.common.util.PaginationUtil;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/bakery")
@AllArgsConstructor
public class BakeryController {

	private final BakeryServiceImpl bakeryService;
	private final ReviewServiceImpl reviewService;

	@GetMapping("/list")
	public String list() {
		return "user/bakery-list";
	}

	@GetMapping("/insert/form")
	public String bakeryInsertForm() {
		return "owner/bakery-insert";
	}

	@GetMapping("/menu/insert/form")
	public String menuInsertForm() {
		return "owner/menu-insert";

	}

	@GetMapping("/detail/form")
	public String detail() {
		return "user/bakery-detail";
	}

	@GetMapping("/detail")
	public String getBakeryImages(@RequestParam(value = "bakeryNo", required = false) double no,
			@RequestParam(value = "currentPage", defaultValue = "1") int currentPage, Model model) {

		/**
		 * 가게 정보 가져오는 기능
		 */
		List<BakeryDto> bakeriesInfo = bakeryService.getBakeryImages(no);
		model.addAttribute("bakeriesInfo", bakeriesInfo);

		List<BakeryDto> getBakeriesInfo = bakeryService.getBakeriesInfo(no);
		model.addAttribute("getBakeriesInfo", getBakeriesInfo);

		// 리뷰 리스트 pagination
		int pageLimit = 5;
		int reviewLimit = 10;

		int totalReviews = reviewService.getTotalReviewCount(no);

		PageResponseDto pageInfo = PaginationUtil.getPageInfo(totalReviews, currentPage, pageLimit, reviewLimit);

		Map<String, Object> result = reviewService.list(pageInfo, currentPage, totalReviews, pageLimit, reviewLimit,no);


		
		model.addAttribute("pi", pageInfo);
		model.addAttribute("reviews", result.get("reviews"));
		model.addAttribute("bakeryNo", no);


		/**
		 * 메뉴 리스트 보여주는 기능
		 */
		List<MenuResponseDto> menuList = bakeryService.getMenuInfo(no);
		model.addAttribute("menuList", menuList);

		/*
		 * 편의정보, 실내사진, 외부 사진 보여지는 기능
		 */

		List<BakeryDto> bakeryDetail = bakeryService.getBakeryDetail(no);
		model.addAttribute("bakeryDetail", bakeryDetail);

		/*
		 * 리뷰 이미지 보여지는 기능
		 */
		List<ReviewResponseDto> reviewImages = reviewService.getReviewImages(no);
		model.addAttribute("reviewImages", reviewImages);
		
		

		return "user/bakery-detail"; // bakeryDetail.html 뷰 반환
	}

	@PostMapping("/cart/add")
	public String addCart(@RequestParam("userNo") int userNo, @RequestParam("orderData") String orderData) {

		ObjectMapper objectMapper = new ObjectMapper();
		List<MenuRequestDto> menuDtoList = new ArrayList<>();

		bakeryService.addCart(userNo, menuDtoList);

		return "user/order-page";
	}

	@GetMapping("/kakao")
	
	public ResponseEntity<BakeryDto> getKakaoMap(@RequestParam("bakeryNo") double bakeryNo){
		
		BakeryDto bakery = bakeryService.getBakeryByNo(bakeryNo);
		
		 if (bakery == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	        return ResponseEntity.ok(bakery);
	    }
		
	}
	
	
	
	
	


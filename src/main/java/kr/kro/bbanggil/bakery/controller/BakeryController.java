package kr.kro.bbanggil.bakery.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.type.TypeReference;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.kro.bbanggil.bakery.dto.BakeryDto;
import kr.kro.bbanggil.bakery.dto.BakeryInfoDTO;
import kr.kro.bbanggil.bakery.dto.BakerySearchDTO;
import kr.kro.bbanggil.bakery.dto.request.BakeryImgRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.BakeryRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.MenuDetailRequestDto;
import kr.kro.bbanggil.bakery.dto.response.MenuResponseDto;
import kr.kro.bbanggil.bakery.dto.response.PageResponseDto;
import kr.kro.bbanggil.bakery.dto.response.ReviewResponseDto;
import kr.kro.bbanggil.bakery.dto.response.bakeryUpdateResponseDTO;
import kr.kro.bbanggil.bakery.service.BakeryServiceImpl;
import kr.kro.bbanggil.bakery.service.ReviewServiceImpl;
import kr.kro.bbanggil.bakery.util.ListPageNation;
import kr.kro.bbanggil.common.dto.PageInfoDTO;
import kr.kro.bbanggil.common.util.PaginationUtil;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/bakery")
@AllArgsConstructor
public class BakeryController {

	private final BakeryServiceImpl bakeryService;
	private final ReviewServiceImpl reviewService;

	private final ListPageNation pageNation;
	

	@GetMapping("/list")
	public String list(@RequestParam(value="currentPage",defaultValue="1")int currentPage,
					   @RequestParam(value="orderType", required=false,defaultValue="recent")String orderType,
					   @ModelAttribute BakerySearchDTO bakerySearchDTO,
						BakeryInfoDTO bakeryInfoDTO,
					    Model model) {
		//전체 게시물
		int postCount = bakeryService.totalCount(bakerySearchDTO);
		int pageLimit = 5;
		int boardLimit = 10;
		System.out.println("현재페이지 = " + currentPage);
		
		Map<String,Object> result = bakeryService.bakeryList(pageNation,
															currentPage,
															postCount,
															pageLimit,
															boardLimit,
															orderType,
															bakerySearchDTO);
		
		PageInfoDTO piResult = (PageInfoDTO) result.get("pi");
		System.out.println(piResult.getCurrentPage());
		
		List<BakeryInfoDTO> postsResult = (List<BakeryInfoDTO>) result.get("posts");
		List<List<BakeryInfoDTO>> imagesResult = (List<List<BakeryInfoDTO>>) result.get("images");
		String todayDayOfWeek = (String) result.get("today");
		List<BakeryInfoDTO> bakeryInfo = new ArrayList<>();
		
		for(int i=0;i<postsResult.size();i++) {
			BakeryInfoDTO post = postsResult.get(i);
			for(int j=0;j<imagesResult.get(i).size();j++) {
				post.setBakeryImageDTO(imagesResult.get(i).get(j).getBakeryImageDTO());
			}
			bakeryInfo.add(post);
		}
		
		model.addAttribute("orderType",orderType);
		model.addAttribute("posts",bakeryInfo);
		model.addAttribute("pi",piResult);
		model.addAttribute("today",todayDayOfWeek);
		model.addAttribute("bakerySearchDTO",bakerySearchDTO);
		
		return "user/bakery-list";
	}
	

	@GetMapping("/insert/form")
	public String bakeryInsertForm(BakeryRequestDTO BakeryRequestDTO,
								   Model model) {
		model.addAttribute(BakeryRequestDTO);
		model.addAttribute("closeWindow", true);
		return "owner/bakery-insert";
	}

	@GetMapping("/menu/insert/form")
	public String menuInsertForm() {
		return "owner/menu-insert";

	}


	/**
	 * 
	 * @param BakeryRequestDTO : insert에 대한 전반적인 데이터가 들어있는 DTO
	 * @param BakeryImgRequestDTO : insert에 필요한 이미지들을 포함하는 DTO
	 * dateSet() : weekday, weekend 데이터 입력 시 각 요일에 맞게 데이터를 넣어주는 메서드
	 * timeSet() : 각 요일에 opentime, closetime를 설정해주는 메서드
	 */
	@PostMapping("/insert")
	public String bakeryInsert(@ModelAttribute @Valid BakeryRequestDTO BakeryRequestDTO,
							   @ModelAttribute BakeryImgRequestDTO BakeryImgRequestDTO,
							   @SessionAttribute("userNum")int userNo,
							   @SessionAttribute("role") String role,
							   Model model) throws Exception {
		System.out.println("에엥");
		System.out.println(role);
		System.out.println("엥");
		BakeryRequestDTO.setTime();
		bakeryService.bakeryInsert(BakeryRequestDTO,BakeryImgRequestDTO,userNo,role);
		
		return "common/home";
	}

	@GetMapping("/detail")
	public String getBakeryImages(@RequestParam(value = "bakeryNo", required = false) double no,
			@RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
			@RequestParam(value = "sort" ,defaultValue= "latest") String sort,
			HttpSession session,
			Model model) {
		
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

		// 리뷰 리스트 pagination
		int pageLimit = 5;
		int reviewLimit = 10;

		int totalReviews = reviewService.getTotalReviewCount(no);

		PageResponseDto pageInfo = PaginationUtil.getPageInfo(totalReviews, currentPage, pageLimit, reviewLimit);

		Map<String, Object> result = reviewService.list(pageInfo, currentPage, totalReviews, pageLimit, reviewLimit,
				no, sort);

		model.addAttribute("pi", pageInfo);
		model.addAttribute("reviews", result.get("reviews"));
		model.addAttribute("bakeryNo", no);
		model.addAttribute("sort", sort);

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

		
		Map<String, Integer> tagCounts = reviewService.getTagCounts(no);
		
		
		
		model.addAttribute("tagCounts", tagCounts);
		model.addAttribute("bakeryNo", no);
		
		model.addAttribute("userNum", userNum);
		
		
		
		
		
		return "user/bakery-detail"; // bakeryDetail.html 뷰 반환
	}

	@PostMapping("/cart/add")
	public String addCart( HttpSession session,@RequestParam("orderData") String orderData) {
		
		System.out.println("dfsfsfsfs!!!!");
		Integer userNo = (Integer) session.getAttribute("userNum");
		
		System.out.println(userNo);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		
		System.out.println("📌 orderData (raw JSON): " + orderData); // 문자열로 넘어온 JSON
		
		
		List<MenuDetailRequestDto> menuDtoList = new ArrayList<>();
		

		bakeryService.addCart(userNo, menuDtoList);

		return "user/order-page";
	}

	@GetMapping("/kakao")

	public ResponseEntity<BakeryDto> getKakaoMap(@RequestParam("bakeryNo") double bakeryNo) {

		BakeryDto bakery = bakeryService.getBakeryByNo(bakeryNo);

		if (bakery == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		 	System.out.println("🔍 가져온 빵집 정보: " + bakery);
	        System.out.println("🔍 가져온 Response 객체: " + bakery.getResponse());
		
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
	public String bakeryUpdateForm(@RequestParam(name="bakeryNo",required=false) Integer bakeryNo,Model model) {
		bakeryUpdateResponseDTO result = bakeryService.getbakeryInfo(bakeryNo);
		model.addAttribute("bakery",result);
		return "owner/bakery-update";
	}
	
	@PostMapping("/update")
	public String bakeryUpdate(BakeryRequestDTO bakeryRequestDTO,
							   BakeryImgRequestDTO bakeryImgRequestDTO,
							   @SessionAttribute("userNum")int userNo) {
		bakeryService.bakeryUpdate(bakeryRequestDTO,bakeryImgRequestDTO,userNo);
		return "/owner/owner-mypage";
	}
	

	

}

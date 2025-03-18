package kr.kro.bbanggil.bakery.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.kro.bbanggil.bakery.dto.BakeryInfoDTO;
import kr.kro.bbanggil.bakery.dto.BakerySearchDTO;
import kr.kro.bbanggil.bakery.service.BakeryServiceImpl;
import kr.kro.bbanggil.bakery.util.ListPageNation;
import kr.kro.bbanggil.common.dto.PageInfoDTO;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.SessionAttribute;


import jakarta.validation.Valid;
import kr.kro.bbanggil.bakery.dto.BakeryDto;
import kr.kro.bbanggil.bakery.dto.request.BakeryInsertImgRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.BakeryInsertRequestDTO;
import kr.kro.bbanggil.bakery.service.BakeryService;
import kr.kro.bbanggil.bakery.service.BakeryServiceImpl;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/bakery")
@AllArgsConstructor
public class BakeryController {

	private final BakeryServiceImpl bakeryService;
	private final ListPageNation pageNation;
	
	public BakeryController(BakeryServiceImpl bakeryService, ListPageNation pageNation) {
		this.bakeryService = bakeryService;
		this.pageNation = pageNation;
	}

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
		
		Map<String,Object> result = bakeryService.bakeryList(pageNation,
															currentPage,
															postCount,
															pageLimit,
															boardLimit,
															orderType,
															bakerySearchDTO);
		
		PageInfoDTO piResult = (PageInfoDTO) result.get("pi");
		
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
	public String bakeryInsertForm(BakeryInsertRequestDTO BakeryRequestDTO,
								   Model model) {
		model.addAttribute(BakeryRequestDTO);
		model.addAttribute("closeWindow", true);
		return "owner/bakery-insert";
	}
	/**
	 * 
	 * @param BakeryRequestDTO : insert에 대한 전반적인 데이터가 들어있는 DTO
	 * @param BakeryImgRequestDTO : insert에 필요한 이미지들을 포함하는 DTO
	 * dateSet() : weekday, weekend 데이터 입력 시 각 요일에 맞게 데이터를 넣어주는 메서드
	 * timeSet() : 각 요일에 opentime, closetime를 설정해주는 메서드
	 */
	@PostMapping("/insert")
	public String bakeryInsert(@ModelAttribute @Valid BakeryInsertRequestDTO BakeryRequestDTO,
							   @ModelAttribute BakeryInsertImgRequestDTO BakeryImgRequestDTO,
							   @SessionAttribute(name="userNo", required=false) int userNo,
							   Model model) throws Exception {
		BakeryRequestDTO.setTime();
		service.bakeryInsert(BakeryRequestDTO,BakeryImgRequestDTO,userNo);
		
		return "common/home";
	}

	
	@GetMapping("/detail/form")
	public String detail() {
		return "user/bakery-detail";
	}
	
	@GetMapping("/detail/{bakeryNo}")
	public String getBakeryImages(@PathVariable("bakeryNo") double no, Model model) {
		
		/**
		 * 가게 정보 가져오는 기능
		 */
	    List<BakeryDto> bakeriesInfo = service.getBakeryImages(no); 
	    model.addAttribute("bakeriesInfo", bakeriesInfo);
	    
	    return "user/bakery-detail"; // bakeryDetail.html 뷰 반환
	}
	
	@GetMapping("/update/form")
	public String bakeryUpdateForm() {
		return "/owner/bakery-update";
	}

	
}

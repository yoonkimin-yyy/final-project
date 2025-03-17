package kr.kro.bbanggil.bakery.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.kro.bbanggil.bakery.dto.BakeryInfoDTO;
import kr.kro.bbanggil.bakery.dto.BakerySearchDTO;
import kr.kro.bbanggil.bakery.service.BakeryServiceImpl;
import kr.kro.bbanggil.bakery.util.ListPageNation;
import kr.kro.bbanggil.common.dto.PageInfoDTO;

@Controller
@RequestMapping("/bakery")
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
	public String bakeryInsertForm() {
		return "owner/bakery-insert";
	}
	@GetMapping("/menu/insert/form")
	public String menuInsertForm() {
		return "owner/menu-insert";
	}
		
	
}

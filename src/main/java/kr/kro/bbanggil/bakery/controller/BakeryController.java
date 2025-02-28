package kr.kro.bbanggil.bakery.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.kro.bbanggil.bakery.dto.BakeryInfoDTO;
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
					    Model model) {
		//전체 게시물
		int postCount = bakeryService.totalCount();
		int pageLimit = 5;
		int boardLimit = 10;
		
		
		
		Map<String,Object> result = bakeryService.bakeryList(pageNation,currentPage,postCount,pageLimit,boardLimit);
		
		PageInfoDTO piResult = (PageInfoDTO) result.get("pi");
		
		List<BakeryInfoDTO> postsResult = (List<BakeryInfoDTO>) result.get("posts");
		String todayDayOfWeek = (String) result.get("today");
		
		for(BakeryInfoDTO item : postsResult) {
			System.out.println(item.getBakeryName());
		}
		
		model.addAttribute("posts",postsResult);
		model.addAttribute("pi",piResult);
		model.addAttribute("today",todayDayOfWeek);
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
		
	// ajax로 추가 리스트를 불러오기 위한 코드
	@GetMapping("/api/list")
	public ResponseEntity<Map<String, Object>> apiList(@RequestParam(value="currentPage",defaultValue="1")int currentPage) {
		
		int boardLimit = 10;
		Map<String, Object> result = bakeryService.bakeryList(pageNation,currentPage,boardLimit,boardLimit,boardLimit);
		
		return ResponseEntity.ok(result);
	}
}

package kr.kro.bbanggil.bakery.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.kro.bbanggil.bakery.dto.BakeryInfoDTO;
import kr.kro.bbanggil.bakery.dto.BakerySearchDTO;
import kr.kro.bbanggil.bakery.service.BakeryServiceImpl;
import kr.kro.bbanggil.bakery.util.ListPageNation;
import kr.kro.bbanggil.common.dto.PageInfoDTO;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiBakeryController {
	private final BakeryServiceImpl bakeryService;
	private final ListPageNation pageNation;

	// ajax로 추가 리스트를 불러오기 위한 코드
		@GetMapping("/list")
		public ResponseEntity<Map<String, Object>> apiList(@RequestParam(value="currentPage",defaultValue="1")int currentPage,
														@RequestParam(value="orderType", required=false,defaultValue="recent")String orderType,
														@ModelAttribute BakerySearchDTO bakerySearchDTO) {
			int postCount = bakeryService.totalCount(bakerySearchDTO);
			int pageLimit = 5;
			int boardLimit = 10;
			Map<String, Object> result = bakeryService.bakeryList(pageNation,currentPage,postCount,pageLimit,boardLimit,orderType,bakerySearchDTO);
			
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
			
			return ResponseEntity.ok(result);
		}
}

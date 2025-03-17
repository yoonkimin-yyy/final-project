package kr.kro.bbanggil.bakery.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.bakery.dto.BakeryInfoDTO;
import kr.kro.bbanggil.bakery.dto.BakerySearchDTO;
import kr.kro.bbanggil.bakery.mapper.BakeryMapper;
import kr.kro.bbanggil.bakery.util.ListPageNation;
import kr.kro.bbanggil.common.dto.PageInfoDTO;

@Service
public class BakeryServiceImpl implements BakeryService {
	private final BakeryMapper bakeryMapper;
	
	public BakeryServiceImpl(BakeryMapper bakeryMapper) {
		this.bakeryMapper = bakeryMapper;
	}
	@Override
	public Map<String, Object> bakeryList(ListPageNation pageNation,
											int currentPage,
											int postCount,
											int pageLimit,
											int boardLimit,
											String orderType,
											BakerySearchDTO bakerySearchDTO){
		String searchText = bakerySearchDTO.getSearchText();
        String[] keywords = searchText.split("\\s+"); // 공백 기준으로 분리

        if (keywords.length >= 2) {
            bakerySearchDTO.setKeyword1(keywords[0]); // 첫 번째 단어 (ex: 경기)
            bakerySearchDTO.setKeyword2(keywords[1]); // 두 번째 단어 (ex: 단팥빵)
        } else {
            bakerySearchDTO.setKeyword1(searchText);  // 단어 하나만 입력된 경우
            bakerySearchDTO.setKeyword2(searchText);
        }
		
		PageInfoDTO pi = pageNation.getPageInfo(postCount, currentPage, pageLimit, boardLimit);
		
		System.out.println(pi.getLimit());
		System.out.println(pi.getOffset());
		List<BakeryInfoDTO> posts = bakeryMapper.bakeryList(pi, 
															getTodayDayOfWeek(),
															orderType,
															bakerySearchDTO);
		
		for(BakeryInfoDTO item : posts) {
			System.out.println(item.getBakeryName());
		}
 		
		List<List<BakeryInfoDTO>> images = new ArrayList<>();
		
		for (int i = 0; i < posts.size(); i++) {
		    images.add(bakeryMapper.bakeryImage(posts.get(i).getBakeryNo()));
		}
		
		
		
			
		
		Map<String,Object> result = new HashMap<>();
		
		result.put("pi", pi);
		result.put("posts", posts);
		result.put("images", images);
//		result.put("today", today);
		
		return result;
	}
	
	// 빵집 수 
	@Override
	public int totalCount(BakerySearchDTO bakerySearchDTO) {
		return bakeryMapper.totalCount(bakerySearchDTO);
	}
	

	
	//오늘 요일 구하기
	@Override
	public String getTodayDayOfWeek() {
		LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        return getKoreanDayOfWeek(dayOfWeek);
	}
	
	private String getKoreanDayOfWeek(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> "월";
            case TUESDAY -> "화";
            case WEDNESDAY -> "수";
            case THURSDAY -> "목";
            case FRIDAY -> "금";
            case SATURDAY -> "토";
            case SUNDAY -> "일";
        };
    }
}

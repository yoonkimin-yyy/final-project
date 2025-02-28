package kr.kro.bbanggil.bakery.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.bakery.dto.BakeryInfoDTO;
import kr.kro.bbanggil.bakery.dto.BakeryScheduleDTO;
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
											int boardLimit){
		
		PageInfoDTO pi = pageNation.getPageInfo(postCount, currentPage, pageLimit, boardLimit);
		
		List<BakeryInfoDTO> posts = bakeryMapper.bakeryList(pi, getTodayDayOfWeek());
		

			
		
		Map<String,Object> result = new HashMap<>();
		
		result.put("pi", pi);
		result.put("posts", posts);
//		result.put("today", today);
		
		return result;
	}
	
	// 빵집 수 
	@Override
	public int totalCount() {
		return bakeryMapper.totalCount();
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

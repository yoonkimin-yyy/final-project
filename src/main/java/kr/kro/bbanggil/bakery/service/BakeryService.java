package kr.kro.bbanggil.bakery.service;

import java.util.Map;

import kr.kro.bbanggil.bakery.util.ListPageNation;

public interface BakeryService {
	public Map<String, Object> bakeryList(ListPageNation pageNation,
										  int currentPage,
										  int postCount,
										  int pageLimit,
										  int boardLimit);
	// 빵집 수
	public int totalCount();
	
	public String getTodayDayOfWeek();
}

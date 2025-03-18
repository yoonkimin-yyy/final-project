package kr.kro.bbanggil.bakery.service;


import java.util.Map;

import kr.kro.bbanggil.bakery.dto.BakerySearchDTO;
import kr.kro.bbanggil.bakery.util.ListPageNation;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.kro.bbanggil.bakery.dto.BakeryDto;
import kr.kro.bbanggil.bakery.dto.request.BakeryInsertImgRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.BakeryInsertRequestDTO;

public interface BakeryService {

	public Map<String, Object> bakeryList(ListPageNation pageNation,
			  int currentPage,
			  int postCount,
			  int pageLimit,
			  int boardLimit,
			  String orderType,
			  BakerySearchDTO bakerySearchDTO);
	// 빵집 수
	public int totalCount(BakerySearchDTO bakerySearchDTO);
	
	public String getTodayDayOfWeek();

	void bakeryInsert(BakeryInsertRequestDTO bakeryRequestDTO, BakeryInsertImgRequestDTO bakeryImgRequestDTO, int userNo) throws Exception;

	public void saveBakery(BakeryDto bakery);
	List<BakeryDto> getBakeriesByRegion(String region);
	
	List<BakeryDto> getPopularBakeries();
	List<BakeryDto> getRecentBakeries();
	List<BakeryDto>getCategoryBakeries(List<BakeryDto> topBakeries);
	List<BakeryDto> getTopFiveOrders();
	
	List<BakeryDto> getBakeryImages(double no);

	void imgInsert(MultipartFile file);

	
	

}

package kr.kro.bbanggil.user.bakery.service;


import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import kr.kro.bbanggil.user.bakery.dto.BakeryDto;
import kr.kro.bbanggil.user.bakery.dto.BakeryImageDTO;
import kr.kro.bbanggil.user.bakery.dto.BakerySearchDTO;
import kr.kro.bbanggil.user.bakery.dto.request.BakeryImgRequestDTO;
import kr.kro.bbanggil.user.bakery.dto.request.BakeryRequestDTO;
import kr.kro.bbanggil.user.bakery.dto.request.MenuDetailRequestDto;
import kr.kro.bbanggil.user.bakery.dto.request.MenuRequestDTO;
import kr.kro.bbanggil.user.bakery.dto.response.BakeryDetailResponseDto;
import kr.kro.bbanggil.user.bakery.dto.response.CategoryResponseDTO;
import kr.kro.bbanggil.user.bakery.dto.response.MenuResponseDto;
import kr.kro.bbanggil.user.bakery.dto.response.MenuUpdateResponseDTO;
import kr.kro.bbanggil.user.bakery.dto.response.bakeryUpdateResponseDTO;
import kr.kro.bbanggil.user.bakery.util.ListPageNation;

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

	public int bakeryInsert(BakeryRequestDTO bakeryRequestDTO, BakeryImgRequestDTO bakeryImgRequestDTO, int userNo,String role) throws Exception;
  
	public void saveBakery(BakeryDto bakery);
	
	List<BakeryDto> getBakeriesByRegion(String region);
	
	List<BakeryDto> getPopularBakeries();
	List<BakeryDto> getRecentBakeries(double bakeryNo);
	List<BakeryDto>getCategoryBakeries(List<BakeryDto> topBakeries);
	List<BakeryDto> getTopFiveOrders();
	
	public List<BakeryDto> getBakeriesTime(double no);
	
	
	List<BakeryDto> getBakeriesInfo(double no);
	
	List<MenuResponseDto> getMenuInfo(double no);
	
	void addCart(int userNo, List<MenuDetailRequestDto> menuDto);
	
	
	BakeryDto getBakeryByNo(double bakeryNo);
	
	List<BakeryDto> getBakeryImages(double no);


	bakeryUpdateResponseDTO getbakeryInfo(int bakeryNo);

	void bakeryUpdate(BakeryRequestDTO bakeryRequestDTO, BakeryImgRequestDTO bakeryImgRequestDTO,int userNo);

	void imgInsert(MultipartFile file);
	
	List<CategoryResponseDTO> getCategory();
	
	
	Map<String, Object> getMenuList(int bakeryNo);
	
	void menuInsert(MenuRequestDTO menuDTO, int bakeryNo, MultipartFile file);
	
	void menuDelete(int bakeryNo);
	
	MenuUpdateResponseDTO getMenuDetail(int menuNo);
	
	void updateMenu(MenuRequestDTO menuDTO, MultipartFile file);
	
	void updateUserCount(int bakeryNo);
	
	List<BakeryImageDTO> getInsideImages(double bakeryNo);
	List<BakeryImageDTO> getOutsideImages(double bakeryNo);
	
	List<BakeryImageDTO> getParkingImages(double bakeryNo);
	

	
	

}

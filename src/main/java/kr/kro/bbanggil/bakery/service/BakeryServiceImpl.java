package kr.kro.bbanggil.bakery.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;

import kr.kro.bbanggil.bakery.api.KakaoController;
import kr.kro.bbanggil.bakery.dto.BakeryTimeSetDTO;
import kr.kro.bbanggil.bakery.dto.request.BakeryInsertImgRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.BakeryInsertRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.MenuRequestDTO;
import kr.kro.bbanggil.bakery.dto.response.CategoryResponseDTO;
import kr.kro.bbanggil.bakery.dto.response.MenuResponseDTO;
import kr.kro.bbanggil.bakery.mapper.BakeryMapper;
import kr.kro.bbanggil.bakery.util.FileUploadUtil;
import kr.kro.bbanggil.bakery.util.LocationSelectUtil;
import kr.kro.bbanggil.bakery.vo.BakeryDetailVO;
import kr.kro.bbanggil.bakery.vo.BakeryInfoVO;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kr.kro.bbanggil.bakery.dto.BakeryDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BakeryServiceImpl implements BakeryService{
	private final BakeryMapper mapper;
	private final FileUploadUtil fileUpload;
	private final KakaoController kakao;
	private final LocationSelectUtil locationSelect;
	private static final Logger logger = LogManager.getLogger(BakeryServiceImpl.class);
	
	@Override
	public List<CategoryResponseDTO> getCategory() {
		return mapper.getCategory();
	}
	@Override
	public MenuResponseDTO menuInsert(MenuRequestDTO menuRequestDTO,MultipartFile file) {
		
		try {
			if(file==null &&!file.isEmpty()) {
					fileUpload.uploadFile(file, menuRequestDTO.getFileRequestDTO(), "menu");
				mapper.menuFileUpload(menuRequestDTO.getFileRequestDTO());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		mapper.menuInsert(menuRequestDTO);
		MenuResponseDTO result = null;
		return result;
	}
	
	
	@Override
	public void bakeryInsert(BakeryInsertRequestDTO bakeryRequestDTO, BakeryInsertImgRequestDTO bakeryImgRequestDTO) throws Exception {
		try {
			// 카카오 api로 사용자가 입력한 주소를 위도, 경도로 변환
		JsonNode location=kakao.getLocationFromAddress(bakeryRequestDTO.getBakeryAddress());
		
			// 주소 앞부분(시,도)을 찾기위한 address
		String[] address = bakeryRequestDTO.getBakeryAddress().split(" ");
		String region = address[0].substring(0,2);
		locationSelect.selectLocation(region);
		
		BakeryInfoVO bakeryVO = BakeryInfoVO.builder()
								.bakeryName(bakeryRequestDTO.getBakeryName())
								.bakeryAddress(bakeryRequestDTO.getBakeryAddress())
								.bakeryPhone(bakeryRequestDTO.getBakeryPhone())
								.latitude(location.get("y").asDouble())
								.longitude(location.get("x").asDouble())
								.region(address[0].substring(0, 2))
								.build();
		
			mapper.bakeryInsert(bakeryVO);
			bakeryRequestDTO.setBakeryNo(bakeryVO.getBakeryNo());
			
		BakeryDetailVO detailVO = BakeryDetailVO.builder()
								  .amenity(bakeryRequestDTO.getParkingInfo())
								  .insideInfo(bakeryRequestDTO.getInsideInfo())
								  .outsideInfo(bakeryRequestDTO.getOutsideInfo())
								  .createdDate(bakeryRequestDTO.getCreatedDate())
								  .bakeryNo(bakeryRequestDTO.getBakeryNo())
								  .build();
			mapper.bakeryDetailInsert(detailVO);
		for(BakeryTimeSetDTO item : bakeryRequestDTO.getTimeDTO().getSetDTO()) {
			mapper.bakeryScheduleInsert(item,bakeryRequestDTO.getBakeryNo());
		}
			
		Map<String,List<MultipartFile>> filemap = new LinkedHashMap<>();
		filemap.put("main", bakeryImgRequestDTO.getMain());
		filemap.put("inside", bakeryImgRequestDTO.getInside());
		filemap.put("outside", bakeryImgRequestDTO.getOutside());
		filemap.put("parking", bakeryImgRequestDTO.getParking());
		for(Map.Entry<String,List<MultipartFile>> entry : filemap.entrySet()) {
			String imgLocation = entry.getKey();
			List<MultipartFile> files = entry.getValue();
			if(bakeryImgRequestDTO.checkFile(files)) {
				for(int i=0;i<files.size();i++) {
					fileUpload.uploadFile(files.get(i), bakeryRequestDTO.getFileDTO(), "bakery");
					bakeryRequestDTO.setImgLocation(imgLocation);
					mapper.bakeryFileUpload(bakeryRequestDTO);
				}
			}
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	

	@Override
	public void saveBakery(BakeryDto bakery) {

		try {
			mapper.insertBakery(bakery);
			logger.info("빵집 정보 저장 성공: {}",bakery.getName());
		}catch(Exception e) {
			logger.error("빵집 정보 저장 실패 : {}, 오류 메세지 :{}",bakery.getName(),e.getMessage(),e);
			throw new RuntimeException("빵집 정보를 저장하는 중 오류 발생");
		}
		

	}

	@Override
	public List<BakeryDto> getBakeriesByRegion(String region) {

		return mapper.getBakeriesByRegion(region);
	}

	@Override
	public List<BakeryDto> getPopularBakeries() {

		return mapper.getPopularBakeries();
	}

	@Override
	public List<BakeryDto> getRecentBakeries() {

		return mapper.getRecentBakeries();
	}

	@Override
	public List<BakeryDto> getCategoryBakeries(List<BakeryDto> topBakeries) {

		List<String> categoryNames = topBakeries.stream().map(bakery -> bakery.getResponse().getCategory()).distinct()
				.collect(Collectors.toList());

		return mapper.getCategoryBakeries(categoryNames);
	}

	@Override
	public List<BakeryDto> getTopFiveOrders() {
		return mapper.getTopFiveOrders();
	}

	@Override
	public List<BakeryDto> getBakeryImages(double no) {

		return mapper.findBakeryImages(no);
	}
	
}

package kr.kro.bbanggil.bakery.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;

import kr.kro.bbanggil.bakery.api.KakaoController;
import kr.kro.bbanggil.bakery.dto.BakeryDto;
import kr.kro.bbanggil.bakery.dto.BakeryTimeSetDTO;
import kr.kro.bbanggil.bakery.dto.request.BakeryInsertImgRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.BakeryInsertRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.MenuRequestDTO;
import kr.kro.bbanggil.bakery.dto.response.CategoryResponseDTO;
import kr.kro.bbanggil.bakery.mapper.BakeryMapper;
import kr.kro.bbanggil.bakery.util.FileUploadUtil;
import kr.kro.bbanggil.bakery.util.LocationSelectUtil;
import kr.kro.bbanggil.bakery.vo.BakeryDetailVO;
import kr.kro.bbanggil.bakery.vo.BakeryInfoVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BakeryServiceImpl implements BakeryService{
	private final BakeryMapper mapper;
	private final FileUploadUtil fileUpload;
	private final KakaoController kakao;
	private final LocationSelectUtil locationSelect;
	private static final Logger logger = LogManager.getLogger(BakeryServiceImpl.class);
	
	/**
	 * location : 카카오 api로 bakeryRequestDTO에 있는 주소 값을 통해 데이터를 받아오는 변수
	 * address : 받아온 주소를 " "기준으로 잘라서 배열에 넣어두는 변수
	 */
	@Override
	@Transactional
	public void bakeryInsert(BakeryInsertRequestDTO bakeryRequestDTO, BakeryInsertImgRequestDTO bakeryImgRequestDTO) throws Exception {
		try {
			JsonNode location=kakao.getLocationFromAddress(bakeryRequestDTO.getBakeryAddress());
				
			String[] address = bakeryRequestDTO.getBakeryAddress().split(" ");
			String region = address[0];
			locationSelect.selectLocation(region);
			int bakeryNo = mapper.getBakeryNo();
			
			BakeryInfoVO bakeryVO = BakeryInfoVO.builder()
									.bakeryName(bakeryRequestDTO.getBakeryName())
									.bakeryAddress(bakeryRequestDTO.getBakeryAddress())
									.bakeryPhone(bakeryRequestDTO.getBakeryPhone())
									.latitude(location.get("y").asDouble())
									.longitude(location.get("x").asDouble())
									.region(address[0].substring(0, 2))
									.build();
			
				mapper.bakeryInsert(bakeryVO);
				bakeryRequestDTO.setBakeryNo(bakeryNo);
				
			BakeryDetailVO detailVO = BakeryDetailVO.builder()
									  .amenity(bakeryRequestDTO.getParkingInfo())
									  .insideInfo(bakeryRequestDTO.getInsideInfo())
									  .outsideInfo(bakeryRequestDTO.getOutsideInfo())
									  .createdDate(bakeryRequestDTO.getCreatedDate())
									  .bakeryNo(bakeryNo)
									  .build();
				mapper.bakeryDetailInsert(detailVO);
				
				for(BakeryTimeSetDTO item : bakeryRequestDTO.getTime()) {
					mapper.bakeryScheduleInsert(item,bakeryNo);
				}
				/**
				 * filemap : 이미지가 들어가지는 위치에 따라 ("이미지의 위치",이미지 내용)으로 매핑되는 변수
				 */
			Map<String,List<MultipartFile>> filemap = new LinkedHashMap<>();
			filemap.put("main", bakeryImgRequestDTO.getMain());
			filemap.put("inside", bakeryImgRequestDTO.getInside());
			filemap.put("outside", bakeryImgRequestDTO.getOutside());
			filemap.put("parking", bakeryImgRequestDTO.getParking());
		
				/**
				 * imgLocation : 이미지가 입력된 위치
				 * files : 해당 위치에 들어간 이미지들
				 */
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

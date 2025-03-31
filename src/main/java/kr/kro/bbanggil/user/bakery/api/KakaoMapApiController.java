package kr.kro.bbanggil.user.bakery.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.kro.bbanggil.user.bakery.dto.BakeryDto;
import kr.kro.bbanggil.user.bakery.dto.response.KakaoPlaceResponseDto;
import kr.kro.bbanggil.user.bakery.service.BakeryServiceImpl;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/kakao")
@RequiredArgsConstructor
public class KakaoMapApiController {
	private final BakeryServiceImpl bakeryService;
	
	@Value("${kakao.api.key}")
	private String KAKAO_API_KEY;
	
	
	/**
	 * kakaoApi에서 query랑 region 입력하면 DB로 데이터 바로 들어감
	 */
	@GetMapping("/fetch")
	public ResponseEntity<String> fetchBakeries(@RequestParam("query") String query, @RequestParam("region") String region) throws JsonMappingException, JsonProcessingException{
		
		RestTemplate restTemplate = new RestTemplate();
		
		String queryWithRegion = query + " " + region;
		String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + queryWithRegion;
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK " + KAKAO_API_KEY);
		headers.set("KA", "os=web; origin=localhost");
		headers.set("User-Agent", "Mozilla/5.0"); 
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,entity, String.class);
		
		 ObjectMapper objectMapper = new ObjectMapper();
		 JsonNode root = objectMapper.readTree(response.getBody());
		 JsonNode documents = root.get("documents");

		
		
		// JSON을 List<KakaoPlaceDto>로 변환
		 List<KakaoPlaceResponseDto> places = objectMapper.readValue(documents.toString(), new TypeReference<List<KakaoPlaceResponseDto>>() {});
		
		 System.out.println(places.size());
		 
		 for(KakaoPlaceResponseDto place : places) {
			
			 if(place.getAddressName().contains(region)) {
				 BakeryDto bakery = new BakeryDto();
				 bakery.setName(place.getPlaceName());
				 bakery.setLatitude(place.getY());
				 bakery.setLongitude(place.getX());
				 bakery.setRegion(region);
				 bakery.setAddress(place.getRoadAddressName() != null ? place.getRoadAddressName() : "주소 없음");
				 bakery.setPhone(place.getPhone() != null ? place.getPhone() : "전화번호 없음");
				 
				 bakeryService.saveBakery(bakery);
			 }
		 }
		 
		
		 return new ResponseEntity<>("Bakeries fetched and saved!", HttpStatus.OK);
	}
	
	

}

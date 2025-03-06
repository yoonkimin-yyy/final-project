package kr.kro.bbanggil.order.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.order.dto.OrderDto;
import kr.kro.bbanggil.order.mapper.OrderMapper;
import lombok.AllArgsConstructor;

//@RequiredArgsConstructor API키 사용하면 
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
	
	private final OrderMapper orderMapper;
	
	// -------------장바구니-----------------------
	@Override
	public List<OrderDto> list(OrderDto orderDto) {

		List<OrderDto> result = orderMapper.list(orderDto);

		return result;
	}
	
	// ----------------총금액 사전검증-----------------------
	@Override
	public boolean accountCheck(int totalCount, OrderDto orderDto) {
		
		int dbTotalCount = 0; // db총 값 저장
		
		List<OrderDto> result = orderMapper.list(orderDto);
		
		for(OrderDto rs : result) {
			dbTotalCount += rs.getMenuPrice() * rs.getMenuCount();
		}
		
		if(totalCount == dbTotalCount) {
			System.out.println("::::::::결과값같음:::::::::::");
			return true;
		} 
		
		return false;
	}
	
	
//	private IamportClient iamportClient;
//	
//	@Value("${rest_api_key}")
//	private String apiKey;
//
//	@Value("${rest_api_secret}")
//	private String secretKey;
//	
//	// IamportClient 객체를 apiKey와 secretKey로 초기화
//	@PostConstruct
//    public void initIamportClient() {
//		this.iamportClient = new IamportClient(apiKey, secretKey);
//	}
	
}

package kr.kro.bbanggil.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.order.request.dto.OrderRequestDto;
import kr.kro.bbanggil.order.request.dto.PaymentRequestDto;
import kr.kro.bbanggil.order.response.dto.OrderResponseDto;


@Mapper
public interface OrderMapper {

	List<OrderResponseDto> list(OrderRequestDto orderRequestDto);
	
	int calculate(OrderRequestDto orderRequestDto);
	
	void save(PaymentRequestDto paymentRequsetDto);

	int findcart();
	
	int findpay(String merchantUid);

	void orderInfo(@Param("cartNo")int cartNo, @Param("payNo") int payNo);

	void pickupCheck(int payNo);
	
}

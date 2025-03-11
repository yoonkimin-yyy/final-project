package kr.kro.bbanggil.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.order.dto.OrderDto;
import kr.kro.bbanggil.order.dto.PaymentDto;

@Mapper
public interface OrderMapper {

	List<OrderDto> list(OrderDto orderDto);
	
	void save(PaymentDto paymentDto);

	int findcart();
	
	int findpay(String merchantUid);

	void orderInfo(@Param("cartNo")int cartNo, @Param("payNo") int payNo);

	void pickupCheck(int payNo);
	
}

package kr.kro.bbanggil.owner.order.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.owner.order.dto.request.OrderRequestDto;
import kr.kro.bbanggil.owner.order.dto.request.PaymentRequestDto;
import kr.kro.bbanggil.owner.order.dto.response.OrderResponseDto;
import kr.kro.bbanggil.owner.order.dto.response.PickupCheckResponseDto;
import kr.kro.bbanggil.user.bakery.dto.response.BakeryResponseDto;


@Mapper
public interface OrderMapper {
	
	List<OrderResponseDto> list(int userNo);
	
	int calculate(int userNo);
	
	void save(PaymentRequestDto paymentRequsetDto);

	int findcart(int userNo);
	
	int findpay(String merchantUid);

	void orderInfo(@Param("cartNo")int cartNo, @Param("payNo") int payNo);

	void pickupCheck(int payNo);

	int pickupPay(int userNo);

	PickupCheckResponseDto pickupStatus(int payNo);

	String refund(int payNo);
	
	Integer countByUserAndOrder(@Param("userNo") Integer userNo, @Param("orderNo") Integer orderNo);

	BakeryResponseDto findRecentOrder(@Param("userNo")int userNo, @Param("bakeryNo")double bakeryNo);

}

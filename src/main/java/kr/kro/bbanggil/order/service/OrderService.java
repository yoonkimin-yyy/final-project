package kr.kro.bbanggil.order.service;

import java.util.List;

import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import kr.kro.bbanggil.bakery.dto.response.BakeryResponseDto;
import kr.kro.bbanggil.bakery.dto.response.PageResponseDto;
import kr.kro.bbanggil.order.dto.request.OrderRequestDto;
import kr.kro.bbanggil.order.dto.request.PaymentRequestDto;
import kr.kro.bbanggil.order.dto.response.OrderResponseDto;
import kr.kro.bbanggil.order.dto.response.PickupCheckResponseDto;


public interface OrderService {
	
	public List<OrderResponseDto> list(int userNo);  

	public boolean accountCheck(int totalCount, int userNo);

	public IamportResponse<Payment> validateIamport(String imp_uid, 
													PaymentRequestDto paymentRequestDto, 
													OrderRequestDto orderRequestDto,
													int userNo);

	public boolean saveOrder(PaymentRequestDto paymentRequestDto, int userNo);

	public IamportResponse<Payment> cancelPayment(String imp_uid);
	
	public List<OrderResponseDto> pickupList(PickupCheckResponseDto result, int payNo, int userNo);
	
	public PickupCheckResponseDto pickupCheckStatus(int payNo);

	public int getPayNo(int userNo);
	
	public boolean isUserOrder(Integer userNo, Integer orderNo);
	
	
	int getOrderCount(String keyword);
	
	List<OrderResponseDto> getPagedOrders(PageResponseDto pi,String keyword);
	
	BakeryResponseDto findOrderNo(int userNum, double bakeryNo);

}

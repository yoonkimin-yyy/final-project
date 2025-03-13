package kr.kro.bbanggil.order.service;

import java.util.HashMap;
import java.util.List;

import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import kr.kro.bbanggil.order.dto.request.OrderRequestDto;
import kr.kro.bbanggil.order.dto.request.PaymentRequestDto;
import kr.kro.bbanggil.order.dto.response.OrderResponseDto;
import kr.kro.bbanggil.order.dto.response.PickupCheckResponseDto;


public interface OrderService {
	
	public List<OrderResponseDto> list();  

	public boolean accountCheck(int totalCount, OrderRequestDto orderRequestDto);

	public IamportResponse<Payment> validateIamport(String imp_uid, 
													PaymentRequestDto paymentRequestDto, 
													OrderRequestDto orderRequestDto);

	public boolean saveOrder(PaymentRequestDto paymentRequestDto);

	public IamportResponse<Payment> cancelPayment(String imp_uid);
	
	public List<OrderResponseDto> pickupList(PickupCheckResponseDto result, int payNo);
	
	public PickupCheckResponseDto pickupCheckStatus(int payNo);

	public int getPayNo();

}

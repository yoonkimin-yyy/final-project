package kr.kro.bbanggil.order.service;

import java.util.List;

import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import kr.kro.bbanggil.order.request.dto.OrderRequestDto;
import kr.kro.bbanggil.order.request.dto.PaymentRequestDto;
import kr.kro.bbanggil.order.response.dto.OrderResponseDto;


public interface OrderService {
	
	public List<OrderResponseDto> list(OrderRequestDto orderRequestDto);  

	public boolean accountCheck(int totalCount, OrderRequestDto orderRequestDto);

	public IamportResponse<Payment> validateIamport(String imp_uid, 
													PaymentRequestDto paymentRequestDto, 
													OrderRequestDto orderRequestDto);

	public boolean saveOrder(PaymentRequestDto paymentRequestDto);

	public IamportResponse<Payment> cancelPayment(String imp_uid);

}

package kr.kro.bbanggil.order.service;

import java.util.List;

import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import kr.kro.bbanggil.order.dto.OrderDto;
import kr.kro.bbanggil.order.dto.PaymentDto;

public interface OrderService {
	
	public List<OrderDto> list(OrderDto orderDto); // String 아이디 

	public boolean accountCheck(int totalCount, OrderDto orderDto);

	public IamportResponse<Payment> validateIamport(String imp_uid, PaymentDto paymentDto, OrderDto orderDto);

	public String saveOrder(PaymentDto paymentDto);

	public IamportResponse<Payment> cancelPayment(String imp_uid);

}

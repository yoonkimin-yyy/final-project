package kr.kro.bbanggil.order.service;

import java.util.List;

import kr.kro.bbanggil.order.dto.OrderDto;

public interface OrderService {
	
	public List<OrderDto> list(OrderDto orderDto);

	public boolean accountCheck(int totalCount, OrderDto orderDto);
}

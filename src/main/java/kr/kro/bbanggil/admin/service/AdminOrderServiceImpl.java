package kr.kro.bbanggil.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.owner.order.dto.response.OrderResponseDto;
import kr.kro.bbanggil.user.bakery.dto.response.PageResponseDto;

@Service
public class AdminOrderServiceImpl implements AdminOrderService{
	
	@Override
	public int getOrderCount(String keyword) {
		return orderMapper.selectOrderCount(keyword);
	}
	
	@Override
	public List<OrderResponseDto> getPagedOrders(PageResponseDto pi, String keyword) {
	    return orderMapper.selectPagedOrders(pi,keyword);
	}
}

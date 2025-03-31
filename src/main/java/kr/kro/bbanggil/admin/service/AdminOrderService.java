package kr.kro.bbanggil.admin.service;

import java.util.List;

import kr.kro.bbanggil.owner.order.dto.response.OrderResponseDto;
import kr.kro.bbanggil.user.bakery.dto.response.PageResponseDto;

public interface AdminOrderService {

	int getOrderCount(String keyword);

	List<OrderResponseDto> getPagedOrders(PageResponseDto pi, String keyword);

}

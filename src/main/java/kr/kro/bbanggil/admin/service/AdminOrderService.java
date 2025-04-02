package kr.kro.bbanggil.admin.service;

import java.util.List;

import kr.kro.bbanggil.common.dto.PageInfoDTO;
import kr.kro.bbanggil.owner.order.dto.response.OrderResponseDto;

public interface AdminOrderService {

	int getOrderCount(String keyword);

	List<OrderResponseDto> getPagedOrders(PageInfoDTO pi, String keyword);

}

package kr.kro.bbanggil.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.admin.mapper.AdminMapper;
import kr.kro.bbanggil.common.dto.PageInfoDTO;
import kr.kro.bbanggil.owner.order.dto.response.OrderResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService{
	
	private final AdminMapper adminMapper;
	
	@Override
	public int getOrderCount(String keyword) {
		return adminMapper.selectOrderCount(keyword);
	}
	
	@Override
	public List<OrderResponseDto> getPagedOrders(PageInfoDTO pi, String keyword) {
	    return adminMapper.selectPagedOrders(pi,keyword);
	}
}

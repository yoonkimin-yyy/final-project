package kr.kro.bbanggil.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.kro.bbanggil.order.dto.OrderDto;

@Mapper
public interface OrderMapper {

	List<OrderDto> list(OrderDto orderDto);
	
}

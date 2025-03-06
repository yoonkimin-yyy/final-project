package kr.kro.bbanggil.order.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.kro.bbanggil.order.dto.OrderDto;
import kr.kro.bbanggil.order.service.OrderService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderPageController {
	
	private final OrderService orderService; 
	
	// 장바구니에 담은 리스트 출력 및 결제하기 유저번호 세션으로 가져오기
	@GetMapping("/page")
	public String order(OrderDto orderDto, Model model) {
		
		List<OrderDto> result = orderService.list(orderDto);
		
		model.addAttribute("results", result);
		
		return "user/order-page";
	}
	
	@GetMapping("/complete")
	public String complete() {
		return "user/order-complete";
	}
	
}

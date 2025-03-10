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
	
	/**
     * 장바구니에 담은 빵 출력
	 */
	@GetMapping("/page")
	public String order(OrderDto orderDto, Model model) { // @SessionAttribute("아이디") String 아이디 
		
		List<OrderDto> result = orderService.list(orderDto);
		
		model.addAttribute("results", result);
		
		return "user/order-page";
	}
	
	@GetMapping("/complete")
	public String complete() {
		return "user/order-complete";
	}
	
}

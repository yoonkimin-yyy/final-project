package kr.kro.bbanggil.order.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.kro.bbanggil.order.request.dto.OrderRequestDto;
import kr.kro.bbanggil.order.response.dto.OrderResponseDto;
import kr.kro.bbanggil.order.service.OrderService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderPageController {
	
	private final OrderService orderService; 
	
	/**
     * 장바구니에 담은 빵 출력
     * 
     * 회원가입로직 완성 시
     * pulic String order에 (@SessionAttribute("아이디") String 아이디) 추가 
     * 유저아이디 (USER_NO)로 리스트를 가져와야 함
	 */
	@GetMapping("/page")
	public String order(OrderRequestDto orderRequestDto, Model model) { 
		
		List<OrderResponseDto> result = orderService.list(orderRequestDto);
		
		model.addAttribute("results", result);
		
		return "user/order-page";
	}
	
	@GetMapping("/complete")
	public String complete() {
		return "user/order-complete";
	}
	
}

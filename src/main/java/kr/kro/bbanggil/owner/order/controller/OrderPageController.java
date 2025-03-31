package kr.kro.bbanggil.owner.order.controller;



import org.springframework.stereotype.Controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.kro.bbanggil.owner.order.dto.response.OrderResponseDto;
import kr.kro.bbanggil.owner.order.service.OrderService;
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
	public String order(@SessionAttribute("userNum") int userNo, Model model) { 
		List<OrderResponseDto> result = orderService.list(userNo);
		
		model.addAttribute("results", result);
		
		return "user/order-page";
	}

	@GetMapping("/complete")
	public String complete(@RequestParam("bakeryName") String bakeryName, Model model) {
		model.addAttribute("bakeryName", bakeryName);
		return "user/order-complete";
	}

	
}

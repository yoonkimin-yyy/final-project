package kr.kro.bbanggil.order.controller;

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	// -----------------총금액 사전검증----------------------
	@PostMapping("/accountCheck")
	@ResponseBody 					// 뷰단에서 총값가져오고 장바구니(db에 있는 값과 비교)
	public Boolean accountCheck(@RequestParam("totalCount") int totalCount,     
							   OrderDto orderDto) {
		
		boolean result = orderService.accountCheck(totalCount, orderDto);
		
		System.out.println(":::::::컨트롤러까지오나요:::::::::");
		System.out.println(result);
		
		return result;
	}
	
	
	// ----------------결제성공 화면 띄우고 db저장-----------------
	@GetMapping("/complete")
	public String complete() {
		return "user/order-complete";
	}
	
}

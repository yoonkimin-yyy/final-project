package kr.kro.bbanggil.order.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.kro.bbanggil.order.dto.OrderDto;
import kr.kro.bbanggil.order.dto.PaymentDto;
import kr.kro.bbanggil.order.service.OrderService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class ApiOrderPageController {
	
	private final OrderService orderService;
	
	// -----------------총금액 사전검증----------------------
	@PostMapping("/accountCheck")
	public Boolean accountCheck(@RequestParam("totalCount") int totalCount,     
							   OrderDto orderDto) {
		
		boolean result = orderService.accountCheck(totalCount, orderDto);
		
		System.out.println(totalCount);
		System.out.println(":::::::컨트롤러까지오나요:::::::::");
		System.out.println(result);
		
		return result;
	}
	
	// ----------------결제성공 화면 띄우고 db저장-----------------
	@PostMapping("/complete")
	public String complete(@RequestBody PaymentDto paymentDto) {
		System.out.println("--------------------");
		System.out.println(paymentDto.getMerchantUid());
		System.out.println(paymentDto.getStatus());
		System.out.println("--------------------");
		
		return "true";
	}
	
}

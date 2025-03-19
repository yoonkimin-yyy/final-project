package kr.kro.bbanggil.order.controller;



import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;


import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/bbanggil")
@RequiredArgsConstructor
public class OrderPageController {



	@GetMapping("/order")
	public String order() {
		return "user/order-page";
	}

	@GetMapping("/complete")
	public String complete() {
		return "user/order-complete";
	}

	
}

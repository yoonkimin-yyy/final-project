package kr.kro.bbanggil.handler.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.kro.bbanggil.order.exception.OrderException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(OrderException.class)
	public String handleOrderException(OrderException oe, Model model) {
		model.addAttribute("message", oe.getMessage());
		model.addAttribute("status", oe.getStatus().value());
		
		return oe.getPath();
	}
	
}

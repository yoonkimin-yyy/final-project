package kr.kro.bbanggil.common.handler;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.kro.bbanggil.bakery.exception.BakeryException;
import kr.kro.bbanggil.order.exception.OrderException;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BakeryException.class)
	public String handleBakeryException(BakeryException be, Model model) {
		model.addAttribute("message",be.getMessage());
		model.addAttribute("status",be.getStatus());
		
		return be.getPath();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public String handleValidException(Model model) {
		model.addAttribute("message","Validation(유효성검증)ㄴ 오류!!!");
		model.addAttribute("status",HttpStatus.BAD_REQUEST);
		
		return "common/error";
		
	}
	
	@ExceptionHandler(OrderException.class)
	public String handleOrderException(OrderException oe, Model model) {
		model.addAttribute("message", oe.getMessage());
		model.addAttribute("status", oe.getStatus().value());
		
		return oe.getPath();
	}
	
}
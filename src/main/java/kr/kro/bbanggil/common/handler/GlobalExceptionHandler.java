package kr.kro.bbanggil.common.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.kro.bbanggil.bakery.exception.BakeryException;
import kr.kro.bbanggil.order.exception.OrderException;
import kr.kro.bbanggil.pickup.exception.PickupException;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BakeryException.class)
	public String handleBakeryException(BakeryException be, Model model) {
		model.addAttribute("message",be.getMessage());
		model.addAttribute("status",be.getStatus());
		
		return be.getPath();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public String handleValidException(Model model,MethodArgumentNotValidException ex) {
		Map<String,String> errors = new HashMap<>();
		for(FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.put(error.getField(), error.getDefaultMessage());
		}
		model.addAttribute("error",errors);
		
		return "common/error";
		
	}
	
	@ExceptionHandler(OrderException.class)
	public String handleOrderException(OrderException oe, Model model) {
		model.addAttribute("message", oe.getMessage());
		model.addAttribute("status", oe.getStatus().value());
		
		return oe.getPath();
	}
	
	
	@ExceptionHandler(PickupException.class)
	public String handlePickupException(PickupException oe, Model model) {
		model.addAttribute("message", oe.getMessage());
		model.addAttribute("status", oe.getStatus().value());
		
		return oe.getPath();
	}
}
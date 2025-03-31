package kr.kro.bbanggil.global.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.kro.bbanggil.global.exception.BbanggilException;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(BbanggilException.class)
	public String handleBakeryException(BbanggilException be, Model model) {
		model.addAttribute("message",be.getMessage());
		model.addAttribute("status",be.getStatus());
		
		return be.getPath();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public String handleValidException(Model model,MethodArgumentNotValidException ex) {
		Map<String,String> errors = new HashMap<>();
		FieldError error =ex.getBindingResult().getFieldError();
			errors.put(error.getField(), error.getDefaultMessage());
		
		model.addAttribute("error",errors);
		    
		return "common/error";
		
	}
	
}
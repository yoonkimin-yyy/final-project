package kr.kro.bbanggil.order.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class OrderException extends RuntimeException {
	
	private final HttpStatus status;
	private final String path;
	
	public OrderException(String message, String path, HttpStatus status) {
		super(message);
		this.path = path;
		this.status = status;
	}
	
}

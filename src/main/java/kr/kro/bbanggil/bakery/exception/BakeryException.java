package kr.kro.bbanggil.bakery.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BakeryException extends RuntimeException {
	private final HttpStatus status;
	private final String path;
	
	public BakeryException(String message, String path, HttpStatus status) {
		super(message);
		this.path = path;
		this.status = status;
	}
}

package kr.kro.bbanggil.pickup.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class PickupException extends RuntimeException{
	
	private final HttpStatus status;
	private final String path;
	
	public PickupException(String message, String path, HttpStatus status) {
		super(message);
		this.path = path;
		this.status = status;
	}
}

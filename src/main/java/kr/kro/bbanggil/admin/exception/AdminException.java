package kr.kro.bbanggil.admin.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class AdminException extends RuntimeException {
	
	private final HttpStatus status;
	private final String path;
	
	public AdminException(String message, String path, HttpStatus status) {
		super(message);
		this.path = path;
		this.status = status;
	}
}

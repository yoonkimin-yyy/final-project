package kr.kro.bbanggil.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BbanggilException extends RuntimeException{

	private final HttpStatus status;
	private final String path;
	
	public BbanggilException(String message, String path, HttpStatus status) {
		super(message);
		this.path = path;
		this.status = status;
	}
}

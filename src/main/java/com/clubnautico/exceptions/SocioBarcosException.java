package com.clubnautico.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SocioBarcosException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public SocioBarcosException(String message) {
		super(message);
	}
	
	

}

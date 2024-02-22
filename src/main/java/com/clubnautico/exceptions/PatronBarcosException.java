package com.clubnautico.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PatronBarcosException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	
	public PatronBarcosException(String message) {
		super(message);
	}

}

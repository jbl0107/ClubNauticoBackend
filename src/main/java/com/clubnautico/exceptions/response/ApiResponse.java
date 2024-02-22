package com.clubnautico.exceptions.response;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

//Esta clase es para personalizar por completo el JSON de la excepcion. Principalmente vamos a quitar la traza 
//del error, dejando el resto de campos

@Data
@NoArgsConstructor

public class ApiResponse {

	private LocalDateTime timestamp = LocalDateTime.now();
	
	private Integer status;
	
	private String error;
	
	private String message;
	
	private String path;

	public ApiResponse(Integer status, String error, String message, String path) {
		this.status = status;
		this.error = error;
		this.message = message;
		
		//cambiamos este formato -> uri="/api/v1/personas/22" por este: "/api/v1/personas/22"
		this.path = path.replace("uri=", "");
	}
	
	
}

package com.clubnautico.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.clubnautico.exceptions.response.ApiResponse;

import jakarta.validation.ConstraintViolationException;

//Esta clase se utiliza para controlar y PERSONALIZAR las excepciones (404, 403, 401, etc).
//Con @RestControllerAdvice, indicamos que esta clase va a detectar todos los errores que se produzcan en nuestros
//controladores.

//@RestControllerAdvice es una especialización de @ControllerAdvice que añade @ResponseBody a todos los métodos, 
//lo que significa que los métodos de manejo de excepciones devuelven directamente la respuesta.

@RestControllerAdvice
public class GlobalExceptionHandler {

	// El @ExceptionHandler sirve para que, una vez que salte la excepcion y se vaya
	// a esta clase, sepa cual es el metodo que debe usar en función del tipo de excepcion. En este caso, se
	// maneja la excepción ResourceNotFoundException.
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handlerResourceNotFOundException(ResourceNotFoundException exception,
			WebRequest webRequest) {

		// Se crea un objeto ApiResponse con los detalles del error y se devuelve en un
		// ResponseEntity con el estado HTTP correspondiente. getDescription(false) es para que solo nos
		// devuelva la uri, y no los detalles del error del cliente. Esto es útil para evitar exponer detalles 
		// sensibles del cliente en la respuesta
		ApiResponse apiResponse = new ApiResponse(404, "Not found", exception.getMessage(),
				webRequest.getDescription(false));

		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);

	}


	// Metodo para validar lo que introducimos en los campos (por ejemplo, por un
		// dni sin numero saltaria esta excepcion)
		@ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException 
				exception, WebRequest webRequest) {

			//getBindingResult().getFieldErrors() da la lista de errores
		    Map<String, String> mapErrors = new HashMap<>();
		    exception.getBindingResult().getFieldErrors().forEach((error) -> { 
		        String clave = error.getField(); // campo que causó la violación
		        String valor = error.getDefaultMessage(); // mensaje de error

		        mapErrors.put(clave, valor);
		    });

		    ApiResponse apiResponse = new ApiResponse(400, "Bad request", mapErrors.toString(), 
		            webRequest.getDescription(false));

		    return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponse> handleConstraintViolationException(ConstraintViolationException exception,
			WebRequest webRequest) {

		Map<String, String> mapErrors = new HashMap<>();
		exception.getConstraintViolations().forEach((error) -> { //getConstraintViolations devuelve lista d errores
			String clave = error.getPropertyPath().toString(); // campo que causó la violación
			String valor = error.getMessage().replace("{", "").replace("=", ""); //mensaje de error correspondiente

			mapErrors.put(clave, valor);
		});

		ApiResponse apiResponse = new ApiResponse(400, "Bad request", mapErrors.toString(), 
				webRequest.getDescription(false));

		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

	}
	

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiResponse> handlerDataIntegrityViolationException(DataIntegrityViolationException 
			exception,
			WebRequest webRequest) {

		String errorMessage = exception.getMessage();
		
		if (errorMessage.contains("Duplicate entry")
				&& errorMessage.contains("for key 'UK5lffrkt5vb4sh090vi8iowxad'")) {
			errorMessage = "El DNI proporcionado ya existe. Por favor, proporciona un DNI único.";
		}
		
		else if (errorMessage.contains("Duplicate entry")
				&& errorMessage.contains("for key 'UK_k13sflu4c6cbqjl168bepwyf6'")) {
			
			errorMessage = "La matrícula proporcionada ya existe. Por favor, proporciona una matrícula única.";
			
		}
		
		else if(errorMessage.contains("FK11nk25wss84fwv5kddvnyutuy")) {
			errorMessage = "El barco indicado no existe";
		}
		
		else if(errorMessage.contains("FKrk5pdr0xp1dftil40fg9rgopn")) {
			errorMessage = "El patron indicado no existe";
		}
		
		else if(errorMessage.contains("UKm2dvbwfge291euvmk6vkkocao")) {
			errorMessage = "El nombre de usuario especificado ya existe";
		}
		
		else if(errorMessage.contains("UK3r2bfhqlmja62e20lj3sulo28")) {
			errorMessage = "El número de socio proporcionado ya existe. Por favor, proporciona un número de socio único.";
		}
		

		ApiResponse apiResponse = new ApiResponse(400, "Bad request", errorMessage,
				webRequest.getDescription(false));

		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

	}
	
	
	@ExceptionHandler(OwnerBarcoException.class)
	public ResponseEntity<ApiResponse> handlerOwnerBarcoException(OwnerBarcoException ownerBarcoException, 
			WebRequest webRequest){
		
		ApiResponse apiResponse = new ApiResponse(400, "Bad Request", ownerBarcoException.getMessage(),
				webRequest.getDescription(false));
		
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse> handlerHttpMessageNotReadableException(HttpMessageNotReadableException 
			exception, WebRequest webRequest){

		String errorMessage = exception.getMessage();
		if(errorMessage.contains("java.time.LocalDateTime")) {
			errorMessage = "El formato de la fecha es incorrecto. Siga este formato: yyyy-MM-dd HH:mm:ss";
		}
		
		ApiResponse apiResponse = new ApiResponse(400, "Bad Request", errorMessage,
				webRequest.getDescription(false));
		
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse> handlerBadCredentialsException(BadCredentialsException exception, 
			WebRequest webRequest){
		
		String errorMessage = "Credenciales incorrectas";
		
		
		ApiResponse apiResponse = new ApiResponse(401, "Unauthorized", errorMessage,
				webRequest.getDescription(false));
		
		return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
		
		
	}
	
	
	@ExceptionHandler(BarcoSalidasException.class)
	public ResponseEntity<ApiResponse> handlerBarcoSalidasException(BarcoSalidasException barcoSalidasException, 
			WebRequest webRequest){
		
		ApiResponse apiResponse = new ApiResponse(400, "Bad Request", barcoSalidasException.getMessage(),
				webRequest.getDescription(false));
		
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(SocioBarcosException.class)
	public ResponseEntity<ApiResponse> handlerSocioBarcosException(SocioBarcosException socioBarcosException, 
			WebRequest webRequest){
		
		ApiResponse apiResponse = new ApiResponse(400, "Bad Request", socioBarcosException.getMessage(),
				webRequest.getDescription(false));
		
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(PatronBarcosException.class)
	public ResponseEntity<ApiResponse> handlerPatronBarcosException(PatronBarcosException patronBarcosException, 
			WebRequest webRequest){
		
		ApiResponse apiResponse = new ApiResponse(400, "Bad Request", patronBarcosException.getMessage(),
				webRequest.getDescription(false));
		
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(PersonaConRolException.class)
	public ResponseEntity<ApiResponse> handlerPersonaConRolException(PersonaConRolException personaConRolException, 
			WebRequest webRequest){
		
		ApiResponse apiResponse = new ApiResponse(400, "Bad Request", personaConRolException.getMessage(),
				webRequest.getDescription(false));
		
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(PatronSalidasException.class)
	public ResponseEntity<ApiResponse> handlerPatronSalidasException(PatronSalidasException patronSalidasException, 
			WebRequest webRequest){
		
		ApiResponse apiResponse = new ApiResponse(400, "Bad Request", patronSalidasException.getMessage(),
				webRequest.getDescription(false));
		
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		
	}
}

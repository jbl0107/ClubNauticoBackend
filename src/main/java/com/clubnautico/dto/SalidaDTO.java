package com.clubnautico.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class SalidaDTO {
	
	private Long id;
	
	@Valid //Sin esta anotacion, JPA no llevara a cabo las validaciones del JSR 380
	@NotNull
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") //este es el formato de la fecha en el json
	private LocalDateTime fechaSalida;
	
	@Valid
	@NotNull(message = "El destino no puede ser nulo")
	@NotEmpty(message = "Destino requerido")
	@NotBlank(message = "El destino no puede estar en blanco")
	private String destino;
	
	private Long barcoId;
	
	private Long patronId;
	
	
	public SalidaDTO(LocalDateTime fechaSalida, String destino) {
		this.fechaSalida = fechaSalida;
		this.destino = destino;
	}
	
	
	
	
	

}

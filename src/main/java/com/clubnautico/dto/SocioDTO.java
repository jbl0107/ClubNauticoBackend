package com.clubnautico.dto;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SocioDTO extends PersonaDTO{
	
	@Valid
	@NotNull(message = "El número de socio no puede ser nulo")
	@Digits(integer = 9, fraction = 0, message = "El numero de socio debe tener 9 cifras")
	private Integer numeroSocio;
	
	
	public SocioDTO(String dni, String nombre, String apellidos, Integer edad, String numTelefono,
			LocalDate fechaNacimiento, Integer numeroSocio) {
		
		super(dni, nombre, apellidos, edad, numTelefono, fechaNacimiento);
		this.numeroSocio = numeroSocio;
	}
	
	@Override
	public String toString() {
		return super.toString() + " Numero de socio [numeroSocio=" + numeroSocio + "]";
	}

}

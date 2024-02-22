package com.clubnautico.dto;

import java.time.LocalDate;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PatronDTO extends PersonaDTO{
	
	public PatronDTO(String dni, String nombre, String apellidos, Integer edad, String numTelefono,
			LocalDate fechaNacimiento) {
		super(dni, nombre, apellidos, edad, numTelefono, fechaNacimiento);
	}

}

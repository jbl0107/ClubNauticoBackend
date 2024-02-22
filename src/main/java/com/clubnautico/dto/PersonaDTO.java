package com.clubnautico.dto;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PersonaDTO {

	private Long id;
	
	@Valid
	@NotEmpty(message = "DNI requerido")
	@NotBlank(message = "El DNI no puede estar en blanco")
	@Pattern(regexp = "^\\d{8}[a-zA-z]{1}$", message = "El DNI no es válido")
	@NotNull(message = "El dni no puede ser nulo")
	private String DNI;

	
	@Valid
	@NotEmpty(message = "Nombre requerido")
	@NotBlank(message = "El nombre no puede estar en blanco")
	@NotNull(message = "El nombre no puede ser nulo")
	@Size(min = 2, max = 25, message = "Este campo no puede tener más de 25 caracteres ni menos de 2")
	private String nombre;

	@Valid
	@NotEmpty(message = "Apellidos requeridos")
	@NotBlank(message = "El apellido no puede estar en blanco")
	@NotNull(message = "Los apellidos no pueden ser nulos")
	@Size(min = 2, max = 50, message = "Este campo no puede tener más de 50 caracteres ni menos de 2")
	private String apellidos;

	@Valid
	@NotNull
	private Integer edad;
	
	
	// (\\+\\d{1,3}\\s?)? -> prefijos internacionales opcionales. E espacio entre el nº y el prefijo tbm opcional
	// \\ es para caracteres especiales. Esto es pq java interpreta la barra invertida como un carácter de 
	//escape, por lo que hay q escaparla con otra barra invertida (si no fuera en java, seria con una \).
	//s = espacio. ? = es opcional.
	@Valid
	@NotNull(message = "El número de teléfono no puede ser nulo")
	@NotBlank(message = "El número de teléfono no puede estar en blanco")
	@NotEmpty(message = "Número de teléfono requerido")
	@Pattern(regexp = "^(\\+\\d{1,3}\\s?)?\\d{9}$", message = "El número de teléfono no es válido")
	private String numTelefono;

	@Valid
	@NotNull(message = "La fecha de nacimiento no puede ser nula")
	private LocalDate fechaNacimiento;

	public PersonaDTO(String DNI, String nombre, String apellidos, Integer edad, String numTelefono,
			LocalDate fechaNacimiento) {
		this.DNI = DNI;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.numTelefono = numTelefono;
		this.fechaNacimiento = fechaNacimiento;
	}

	
	
	
}

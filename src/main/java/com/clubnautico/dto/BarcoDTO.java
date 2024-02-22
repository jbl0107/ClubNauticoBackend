package com.clubnautico.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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
public class BarcoDTO {
	
	private Long id;
	
	@Valid
	@NotEmpty(message = "Matrícula requerida")
	@NotBlank(message = "La matrícula no puede estar en blanco")
	@NotNull
	@Pattern(regexp = "^\\d{3}[a-zA-z]{3}.{3}$")
	private String matricula;
	
	@Valid
	@NotEmpty(message = "Nombre requerido")
	@NotNull
	@NotBlank(message = "El nombre no puede estar en blanco")
	@Size(min = 2, max = 50, message = "Este campo no puede tener más de 50 caracteres ni menos de 2")
	private String nombre;
	
	@Valid
	@NotNull
	private Integer numeroAmarre;
	
	@Valid
	@NotNull
	@DecimalMin(value = "0.0", inclusive = true) // el inclusive indica que el valor de value está incluido en el rango
	@DecimalMax(value = "9999999.99", inclusive = true) // por tanto, el rango es [0.0-9999999.99]
	private Double cuota;
	
	private Long socioId;
	
	private Long patronId;
	
	
	public BarcoDTO(String matricula, String nombre, Integer numeroAmarre, Double cuota) {
		this.matricula = matricula;
		this.nombre = nombre;
		this.numeroAmarre = numeroAmarre;
		this.cuota = cuota;
	}
	
	
	
	

}

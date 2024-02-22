package com.clubnautico.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "socios", uniqueConstraints = {@UniqueConstraint(columnNames = {"numeroSocio"})})
@Getter
@Setter
@NoArgsConstructor
public class Socio extends Persona {

	private static final long serialVersionUID = 1L;

	@Column(name = "numeroSocio", unique = true, nullable = false)
	private Integer numeroSocio;


	public Socio(String dni, String nombre, String apellidos, Integer edad, String numTelefono,
			LocalDate fechaNacimiento, Integer numeroSocio) {
		super(dni, nombre, apellidos, edad, numTelefono, fechaNacimiento);
		this.numeroSocio = numeroSocio;
	}

	@Override
	public String toString() {
		return super.toString() + " Numero de socio [numeroSocio=" + numeroSocio + "]";
	}
	
	
	

}

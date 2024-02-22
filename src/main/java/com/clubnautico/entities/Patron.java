package com.clubnautico.entities;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "patrones")
@Getter
@Setter
@NoArgsConstructor
public class Patron extends Persona {

	private static final long serialVersionUID = 1L;

	// como un patron puede ser socio (pero socio no puede ser patron) se hace esta
	// relacion unidireccional
	@OneToOne
	private Socio socio;

	@OneToMany(mappedBy = "patron", cascade = CascadeType.ALL)
	private Set<Salida> salidas;

	@OneToMany(mappedBy = "patron", cascade = CascadeType.ALL)
	private Set<Barco> barcos;

	public Patron(String dni, String nombre, String apellidos, Integer edad, String numTelefono,
			LocalDate fechaNacimiento) {
		super(dni, nombre, apellidos, edad, numTelefono, fechaNacimiento);
	}

	// Metodo para comprobar si el patron es socio en base a la relacion OneToOne
	public boolean esSocio() {
		return this.socio != null;
	}

}

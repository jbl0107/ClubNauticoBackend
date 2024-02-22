package com.clubnautico.entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
//hace que cada clase q herede de esta tenga su propia tabla. La subclase tendra una FK con la tabla Persona
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "personas", uniqueConstraints = 
							{@UniqueConstraint(columnNames = {"DNI"})})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//@NonNull -> genera una comprobación de nulidad en el método setter durante la compilación. Si se asigna null al 
	//campo DNI, se lanzará una NullPointerException antes de que se realicen las validaciones de Bean, como por ej
	//NotNull, obviandola. Es decir, está anulando al @NotNull
	@Column(name = "dni", nullable = false)
	private String DNI;

	
	@Column(name = "nombre", nullable = false)
	private String nombre;

	
	@Column(name = "apellidos", nullable = false)
	private String apellidos;

	
	@Column(name = "edad", nullable = false)
	private Integer edad;
	
	
	@Column(name = "numTelefono", nullable = false)
	private String numTelefono;
	
	
	@Column(name = "fechaNacimiento", nullable = false)
	private LocalDate fechaNacimiento;


	public Persona(String DNI, String nombre, String apellidos, Integer edad, String numTelefono, 
			LocalDate fechaNacimiento) {
		this.DNI = DNI;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.edad = edad;
		this.numTelefono = numTelefono;
		this.fechaNacimiento = fechaNacimiento;
	}

	
	

}

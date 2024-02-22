package com.clubnautico.entities;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "barcos")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Barco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	//@NonNull //se incluye en el constructor con params. Si el atr no lo tiene, no se incluye
	@Column(name = "matricula", unique = true, nullable = false)
	private String matricula;

	
	@Column(name = "nombre", nullable = false)
	private String nombre;

	
	@Column(name = "amarre", nullable = false)
	private Integer numeroAmarre;

	
	@Column(name = "cuota", nullable = false)
	private Double cuota;

	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(name = "barcoSocio", joinColumns = @JoinColumn(name = "barcoId"), 
	                                   inverseJoinColumns = @JoinColumn(name = "socioId"))
	private Socio socio;

	// Explicacion JoinTable: joinColumns hace referencia a la tabla d la entidad q
	// posee la asociación.
	// inverseColumns especifica las columnas de clave externa d la tabla de unión q
	// hacen referencia a la tabla
	// de la entidad que no posee la asociación.
	@ManyToOne(fetch = FetchType.LAZY)
	@ToString.Exclude
	@JoinTable(name = "barcoPatron", joinColumns = @JoinColumn(name = "barcoId"), 
	                                    inverseJoinColumns = @JoinColumn(name = "patronId"))
	private Patron patron;

	@OneToMany(mappedBy = "barco")
	@ToString.Exclude
	private Set<Salida> salidas;

	public Barco(String matricula, String nombre, Integer numeroAmarre, Double cuota) {
		this.matricula = matricula;
		this.nombre = nombre;
		this.numeroAmarre = numeroAmarre;
		this.cuota = cuota;
	}
	
	
	


	

}

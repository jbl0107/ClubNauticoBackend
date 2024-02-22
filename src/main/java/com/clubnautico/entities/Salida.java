package com.clubnautico.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "salidas")
@Getter
@Setter
@NoArgsConstructor
public class Salida implements Serializable{


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	@Column(name = "fechaSalida", nullable = false)
	private LocalDateTime fechaSalida;

	
	@Column(name = "destino", nullable = false)
	private String destino;

	@Valid
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "barcoId", nullable = false)
	private Barco barco;
	
	@Valid
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull //esto es a nivel de aplicacion, que se verifica antes de que JPA intente persistir la entidad en la DB. 
			//solo se aplica cnd se intenta persistir la entidad a través d JPA. Todas las anotaciones dl JSR380 les pasa lo mismo
	@JoinColumn(name = "patronId", nullable = false) //el nullable es a nivel de DB
	private Patron patron;
	

	
	public Salida(LocalDateTime fechaSalida, String destino) {
		this.fechaSalida = fechaSalida;
		this.destino = destino;
	}


	@Override
	public String toString() {
		return "Salida [id=" + id + ", fechaSalida=" + fechaSalida + ", destino=" + destino + ", barco="
					+ barco.getMatricula() + ", patron=" + patron.getNombre() + "]";
	}

}

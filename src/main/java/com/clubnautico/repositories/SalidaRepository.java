package com.clubnautico.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clubnautico.entities.Salida;

public interface SalidaRepository extends JpaRepository<Salida, Long>{
	
	List<Salida> findByBarcoId(long id);

}

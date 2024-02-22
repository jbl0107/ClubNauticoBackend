package com.clubnautico.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clubnautico.entities.Barco;

public interface BarcoRepository extends JpaRepository<Barco, Long>{

	@Query("SELECT b FROM Barco b WHERE b.socio.id = :socioId")
	List<Barco> findBySocioId(@Param("socioId") long socioId);
	
	@Query("SELECT b FROM Barco b WHERE b.patron.id = :patronId")
	List<Barco> findByPatronId(@Param("patronId") long patronId);

}

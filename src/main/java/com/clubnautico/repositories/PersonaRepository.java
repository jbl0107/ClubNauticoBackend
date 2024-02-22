package com.clubnautico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clubnautico.entities.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long>{

}

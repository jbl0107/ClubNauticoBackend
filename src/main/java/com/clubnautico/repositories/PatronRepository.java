package com.clubnautico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clubnautico.entities.Patron;

public interface PatronRepository extends JpaRepository<Patron, Long>{

}

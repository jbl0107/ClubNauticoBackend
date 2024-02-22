package com.clubnautico.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clubnautico.entities.Rol;
import com.clubnautico.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{


	public Optional<Usuario> findByUsername(String username);
	
	Usuario findByRole(Rol role);
	


}

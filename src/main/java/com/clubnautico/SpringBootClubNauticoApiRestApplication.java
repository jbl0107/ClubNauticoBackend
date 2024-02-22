package com.clubnautico;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.clubnautico.entities.Rol;
import com.clubnautico.entities.Usuario;
import com.clubnautico.repositories.UsuarioRepository;

@SpringBootApplication
public class SpringBootClubNauticoApiRestApplication implements CommandLineRunner{
	
	@Autowired
	private UsuarioRepository usuarioRepositoryImpl;
	
	@Bean
	public ModelMapper modelmapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootClubNauticoApiRestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Usuario user = usuarioRepositoryImpl.findByRole(Rol.ADMIN);
		//Set<Rol> roles = new HashSet<>();
		//roles.add(Rol.ADMIN);
		if(null == user) {
			Usuario userAdmin = new Usuario();
			userAdmin.setEmail("email@gmail.com");
			userAdmin.setFirstName("Ejemplo 1");
			userAdmin.setLastName("apellido ejemplo");
			userAdmin.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userAdmin.setRole(Rol.ADMIN);
			userAdmin.setUsername("admin");
			
			usuarioRepositoryImpl.save(userAdmin);
		}
		
		
		
	}

}

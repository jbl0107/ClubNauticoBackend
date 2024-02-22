package com.clubnautico.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.clubnautico.entities.Usuario;
import com.clubnautico.repositories.UsuarioRepository;
import com.clubnautico.services.UsuarioService;


////no se crea una interfaz UsuarioService pq extiende de la de Spring
@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository usuarioRepositoryImpl;

	
	
	@Override
	public UserDetailsService userDetailsService() {
		
		return new UserDetailsService() {
			
			
			@Override
			public UserDetails loadUserByUsername(String username) {
			    Usuario usuario = usuarioRepositoryImpl.findByUsername(username).orElseThrow(() -> 
			        new UsernameNotFoundException("Usuario no encontrado"));

			    GrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRole().name());

			    return new User(usuario.getUsername(), usuario.getPassword(), List.of(authority));
			}

	
		
		};
	}
}
	



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<Usuario> userDetails = usuarioRepositoryImpl.findByUsername(username);
//		Usuario res;
//		if(!userDetails.isPresent()) {
//			throw new ResourceNotFoundException("El usuario especificado no existe");
//		}else {
//			res = userDetails.get();
//		}
//		
//		//Hay q decirtle a spring security que este es el usuario q va utilizar. Como en successfullAuthentication
//		//JwtAuthenticationFilter indicamos que usamos un usuario de tipo User de spring security, hay que devolver 
//		//un usuario de este tipo. Ademas de username y password, hay q pasarle: 1ro - 4 booleans: 
//		//enable(el usuario esta activo?), accountNonExpired(la cuenta expira? true=no expira),  
//		//credentialsNonExpired(las credenciales expiran?true=no expira), 
//		//accountNonExpired(la cuenta se bloquea? true=no expira)
//		//2do - una coleccion de authorities (permisos/roles q vamos a manejar). Para esto, hay q convertir el set 
//		//de los roles de tipo Rol, a esa coleccion de authorities:
//		
//		Collection<? extends GrantedAuthority> authorities = res.getRoles()
//			    .stream()   //MUY IMPORTANE: PONER ROLE_ PARA ESPECIFICAR EL ROL, SI NO DARÁ MUCHOS PROBLEMAS
//			    .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombre().name()))
//			    .collect(Collectors.toSet()); //LO GUARDAMOS EN UN CONJUNTO
//
//		
//		
//		return new User(res.getUsername(), res.getPassword(), true, true, true, true, authorities);
		
		
		
		
		

//	}
//
//}



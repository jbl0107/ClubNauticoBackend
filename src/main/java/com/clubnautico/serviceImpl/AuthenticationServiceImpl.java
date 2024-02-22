package com.clubnautico.serviceImpl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clubnautico.dto.UsuarioDTO;
import com.clubnautico.entities.Rol;
import com.clubnautico.entities.Usuario;
import com.clubnautico.repositories.UsuarioRepository;
import com.clubnautico.request.LoginRequest;
import com.clubnautico.request.RefreshTokenRequest;
import com.clubnautico.security.jwt.JwtAuthenticationResponse;
import com.clubnautico.services.AuthenticationService;
import com.clubnautico.services.JwtService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
	
	@Autowired
	private UsuarioRepository usuarioRepositoryImpl;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtService jwtServiceImpl;
	

	

	
	public JwtAuthenticationResponse login(LoginRequest request) {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getUsername(), request.getPassword()));
		
		var user = usuarioRepositoryImpl.findByUsername(request.getUsername()).orElseThrow(() -> 
		                               new IllegalArgumentException("Contraseña o usuario invalido"));
		
		var jwtToken = jwtServiceImpl.generateToken(user);
		var refreshToken = jwtServiceImpl.generateRefreshToken(new HashMap<>(), user);
		
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
		jwtAuthenticationResponse.setToken(jwtToken);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);
		return jwtAuthenticationResponse;
		
	}

	public UsuarioDTO register(UsuarioDTO request) {
		Usuario usuario = new Usuario();
//		Set<Rol> roles = new HashSet<>();
//		roles.add(Rol.USER);
		usuario.setUsername(request.getUsername());
		usuario.setPassword(passwordEncoder.encode(request.getPassword()));
		usuario.setFirstName(request.getFirstName());
		usuario.setLastName(request.getLastName());
		usuario.setEmail(request.getEmail());
		usuario.setRole(Rol.USER);
		
		usuarioRepositoryImpl.save(usuario);
		
		UsuarioDTO res = new UsuarioDTO();
		res.setUsername(usuario.getUsername());
		res.setPassword(usuario.getPassword());
		res.setFirstName(usuario.getFirstName());
		res.setLastName(usuario.getLastName());
		res.setEmail(usuario.getEmail());
		
		
		return res;
		

	}
	
	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request) {
		String username = jwtServiceImpl.getUsernameFromToken(request.getToken());
		Usuario usuario = usuarioRepositoryImpl.findByUsername(username).orElseThrow();
		if(jwtServiceImpl.isTokenValid(request.getToken(), usuario)) {
			var jwtToken = jwtServiceImpl.generateToken(usuario);
			
			
			JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
			jwtAuthenticationResponse.setToken(jwtToken);
			jwtAuthenticationResponse.setRefreshToken(request.getToken());
			return jwtAuthenticationResponse;
		}
		return null;
	}
	


}

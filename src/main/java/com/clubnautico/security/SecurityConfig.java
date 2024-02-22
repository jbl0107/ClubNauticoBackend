package com.clubnautico.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.clubnautico.entities.Rol;
import com.clubnautico.security.jwt.JwtAuthenticationFilter;
import com.clubnautico.services.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private UsuarioService usuarioServiceImpl;
	
	
	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		
		return httpSecurity
				.csrf(csrf -> csrf.disable())
				//configuramos los cors para q cualquiera pueda acceder a la API. Tbm podemos especificarlo a nivel
				//de controlador (en el de personas hay un ejemplo)
				.cors(cors -> cors.configurationSource(request -> {
		            CorsConfiguration configuration = new CorsConfiguration();
		            configuration.setAllowedOrigins(Arrays.asList("*")); // Permitir todos los orígenes
		            // Permitir métodos específicos
		            configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
		            
		            // Permite credenciales en las solicitudes. Si los origines =*, esto no puede ser true
		            //configuration.setAllowCredentials(true);
		            // Permitir encabezados específicos
		            configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
		            return configuration;
		        }))
				.authorizeHttpRequests(authRequest -> authRequest
		        		.requestMatchers("/api/v1/auth/**").permitAll()
		        		//.requestMatchers("/api/v1/personas/**").hasAnyAuthority(Rol.ADMIN.name())
		        		.anyRequest().authenticated())
				.sessionManagement(session -> session
		        		.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
		        
		        .build(); 
		
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(usuarioServiceImpl.userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
		
	}
}

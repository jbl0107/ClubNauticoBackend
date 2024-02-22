package com.clubnautico.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.clubnautico.services.JwtService;
import com.clubnautico.services.UsuarioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//OncePerRequestFilter permite crear filtros personalizados
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UsuarioService usuarioServiceImpl;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String token = getTokenFromRequest(request);
		String username;
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
				
		if(!StringUtils.hasLength(authHeader) || !StringUtils.startsWithIgnoreCase(authHeader, "bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		username = jwtService.getUsernameFromToken(token);
		
		if(StringUtils.hasLength(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = usuarioServiceImpl.userDetailsService().loadUserByUsername(username);
			
			//validamos si el token es valido
			if(jwtService.isTokenValid(token, userDetails)) {
				
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				
				UsernamePasswordAuthenticationToken authToken = new 
						UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				securityContext.setAuthentication(authToken);
				SecurityContextHolder.setContext(securityContext);
				
			}
			
		}
		
	filterChain.doFilter(request, response);
		
	}

	
	
	//Metodo para obtener el token
	private String getTokenFromRequest(HttpServletRequest request) {
		
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7); //devuelve desde el caracter 7 hacia delante (los 7 1ros es "Bearer ")
		}
		
		return null;

	}
	
	

	
}

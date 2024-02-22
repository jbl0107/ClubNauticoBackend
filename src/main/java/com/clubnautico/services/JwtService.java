package com.clubnautico.services;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;


public interface JwtService {
	
	public String generateToken(UserDetails userDetails);
	
	public String getUsernameFromToken(String token);
	
	public boolean isTokenValid(String token, UserDetails userDetails);

	public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);

}

package com.clubnautico.security.jwt;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
	
	private String token;
	
	private String refreshToken;

}

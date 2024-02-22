package com.clubnautico.services;

import com.clubnautico.dto.UsuarioDTO;
import com.clubnautico.request.LoginRequest;
import com.clubnautico.request.RefreshTokenRequest;
import com.clubnautico.security.jwt.JwtAuthenticationResponse;

public interface AuthenticationService {
	
	public UsuarioDTO register(UsuarioDTO request);
	public JwtAuthenticationResponse login(LoginRequest request);
	public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request);

}

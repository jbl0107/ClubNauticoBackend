package com.clubnautico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clubnautico.dto.UsuarioDTO;
import com.clubnautico.request.LoginRequest;
import com.clubnautico.request.RefreshTokenRequest;
import com.clubnautico.security.jwt.JwtAuthenticationResponse;
import com.clubnautico.services.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationService authServiceImpl;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthenticationResponse> login(@Valid @RequestBody LoginRequest request) {
		return new ResponseEntity<>(authServiceImpl.login(request), HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UsuarioDTO> register(@Valid @RequestBody UsuarioDTO request) {
		return new ResponseEntity<>(authServiceImpl.register(request), HttpStatus.OK);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
		return new ResponseEntity<>(authServiceImpl.refreshToken(request), HttpStatus.OK);
	}

}

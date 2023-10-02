package com.meommu.meommuapi.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.meommu.meommuapi.auth.dto.LoginRequest;
import com.meommu.meommuapi.auth.dto.TokenResponse;
import com.meommu.meommuapi.auth.service.AuthService;

import jakarta.validation.Valid;

@RestController
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/api/v1/kindergartens/signin")
	public TokenResponse login(@Valid @RequestBody LoginRequest loginRequest) {
		return authService.signin(loginRequest);
	}
}

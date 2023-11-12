package com.meommu.meommuapi.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meommu.meommuapi.auth.dto.SignInRequest;
import com.meommu.meommuapi.auth.dto.TokenResponse;
import com.meommu.meommuapi.auth.service.AuthService;

import jakarta.validation.Valid;

@RestController
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/kindergartens/signin")
	public TokenResponse signIn(@Valid @RequestBody SignInRequest signInRequest) {
		return authService.signIn(signInRequest);
	}
}

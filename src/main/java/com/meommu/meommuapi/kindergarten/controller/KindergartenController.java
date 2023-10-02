package com.meommu.meommuapi.kindergarten.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meommu.meommuapi.auth.dto.AuthInfo;
import com.meommu.meommuapi.auth.token.Auth;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.kindergarten.dto.EmailRequest;
import com.meommu.meommuapi.kindergarten.dto.SignUpRequest;
import com.meommu.meommuapi.kindergarten.service.KindergartenService;

import jakarta.validation.Valid;

@RestController
public class KindergartenController {

	private final KindergartenService kindergartenService;

	public KindergartenController(KindergartenService kindergartenService) {
		this.kindergartenService = kindergartenService;
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/kindergartens/signup")
	public void signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
		kindergartenService.signUp(signUpRequest);
	}

	@GetMapping("/api/v1/kindergartens/email")
	public void checkEmailDuplication(@Valid @ModelAttribute EmailRequest emailRequest) {
		kindergartenService.existsByEmail(emailRequest);
	}
}

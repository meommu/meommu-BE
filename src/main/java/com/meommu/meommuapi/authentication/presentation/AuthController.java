package com.meommu.meommuapi.authentication.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meommu.meommuapi.authentication.dto.AuthInfo;
import com.meommu.meommuapi.authentication.dto.SignInRequest;
import com.meommu.meommuapi.authentication.dto.ReissueRequest;
import com.meommu.meommuapi.authentication.dto.TokenResponse;
import com.meommu.meommuapi.authentication.application.AuthService;
import com.meommu.meommuapi.authentication.configuration.Auth;

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

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/kindergartens/reissue")
	public TokenResponse reissue(@Auth AuthInfo authInfo, @Valid @RequestBody ReissueRequest reissueRequest) {
		return authService.reissue(authInfo, reissueRequest);
	}

	@DeleteMapping("/api/v1/kindergartens/signout")
	public void signOut(@Auth AuthInfo authInfo) {
		authService.signOut(authInfo);
	}
}

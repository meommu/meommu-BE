package com.meommu.meommuapi.auth.service;

import org.springframework.stereotype.Service;

import com.meommu.meommuapi.auth.dto.SignInRequest;
import com.meommu.meommuapi.auth.dto.TokenResponse;
import com.meommu.meommuapi.auth.exception.LoginFailedException;
import com.meommu.meommuapi.auth.token.JwtTokenProvider;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.kindergarten.repository.KindergartenRepository;

@Service
public class AuthService {

	private final KindergartenRepository kindergartenRepository;

	private final Encryptor encryptor;

	private final JwtTokenProvider jwtTokenProvider;

	public AuthService(KindergartenRepository kindergartenRepository, Encryptor encryptor,
		JwtTokenProvider jwtTokenProvider) {
		this.kindergartenRepository = kindergartenRepository;
		this.encryptor = encryptor;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public TokenResponse signIn(SignInRequest signInRequest) {
		Kindergarten kindergarten = kindergartenRepository.findByEmailValueAndPasswordValue(signInRequest.getEmail(),
			encryptor.encrypt(signInRequest.getPassword())).orElseThrow(() -> new LoginFailedException());
		String accessToken = jwtTokenProvider.createAccessToken(kindergarten.getId());
		return TokenResponse.from(accessToken);
	}
}

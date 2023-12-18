package com.meommu.meommuapi.core.auth.service;

import org.springframework.stereotype.Service;

import com.meommu.meommuapi.core.auth.dto.SignInRequest;
import com.meommu.meommuapi.core.auth.dto.TokenResponse;
import com.meommu.meommuapi.core.auth.exception.SignInFailedException;
import com.meommu.meommuapi.core.auth.token.JwtTokenProvider;
import com.meommu.meommuapi.core.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.core.kindergarten.repository.KindergartenRepository;

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
			encryptor.encrypt(signInRequest.getPassword())).orElseThrow(() -> new SignInFailedException());
		String accessToken = jwtTokenProvider.createAccessToken(kindergarten.getId());
		return TokenResponse.from(accessToken);
	}
}

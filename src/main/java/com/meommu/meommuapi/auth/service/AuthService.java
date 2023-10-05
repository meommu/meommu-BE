package com.meommu.meommuapi.auth.service;

import org.springframework.stereotype.Service;

import com.meommu.meommuapi.auth.dto.LoginRequest;
import com.meommu.meommuapi.auth.dto.TokenResponse;
import com.meommu.meommuapi.auth.token.JwtTokenProvider;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.kindergarten.exception.KindergartenNotFoundException;
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

	public TokenResponse signin(LoginRequest loginRequest) {
		Kindergarten kindergarten = kindergartenRepository.findByEmailValueAndPasswordValue(loginRequest.getEmail(),
			encryptor.encrypt(loginRequest.getPassword())).orElseThrow(() -> new KindergartenNotFoundException());
		String accessToken = jwtTokenProvider.createAccessToken(kindergarten.getId());
		return TokenResponse.from(accessToken);
	}
}

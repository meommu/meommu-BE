package com.meommu.meommuapi.core.auth.service;

import static com.meommu.meommuapi.core.auth.exception.errorCode.AuthErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.core.auth.dto.AuthInfo;
import com.meommu.meommuapi.core.auth.dto.ReissueRequest;
import com.meommu.meommuapi.core.auth.dto.SignInRequest;
import com.meommu.meommuapi.core.auth.dto.TokenResponse;
import com.meommu.meommuapi.core.auth.exception.JwtException;
import com.meommu.meommuapi.core.auth.exception.SignInFailedException;
import com.meommu.meommuapi.core.auth.repository.RefreshTokenRepository;
import com.meommu.meommuapi.core.auth.token.JwtTokenProvider;
import com.meommu.meommuapi.core.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.core.kindergarten.repository.KindergartenRepository;

@Transactional(readOnly = true)
@Service
public class AuthServiceImpl implements AuthService{

	private final KindergartenRepository kindergartenRepository;

	private final Encryptor encryptor;

	private final RefreshTokenRepository refreshTokenRepository;

	private final JwtTokenProvider jwtTokenProvider;

	public AuthServiceImpl(KindergartenRepository kindergartenRepository, Encryptor encryptor,
		RefreshTokenRepository refreshTokenRepository, JwtTokenProvider jwtTokenProvider) {
		this.kindergartenRepository = kindergartenRepository;
		this.encryptor = encryptor;
		this.refreshTokenRepository = refreshTokenRepository;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	@Transactional
	public TokenResponse signIn(SignInRequest request) {
		Kindergarten kindergarten = kindergartenRepository.findByEmailValueAndPasswordValue(request.getEmail(),
			encryptor.encrypt(request.getPassword())).orElseThrow(() -> new SignInFailedException());
		String accessToken = jwtTokenProvider.createAccessToken(kindergarten.getId());
		String refreshToken = jwtTokenProvider.createRefreshToken(kindergarten.getId());
		Long expiration = jwtTokenProvider.getExpiration(refreshToken);
		refreshTokenRepository.save(kindergarten.getId(), refreshToken, expiration);
		return TokenResponse.from(accessToken, refreshToken);
	}

	@Override
	@Transactional
	public TokenResponse reissue(AuthInfo authInfo, ReissueRequest request) {
		jwtTokenProvider.validateRefreshToken(request.getRefreshToken());
		Long id = authInfo.getId();
		String refreshToken = refreshTokenRepository.findByUserId(id);
		if (!refreshToken.equals(request.getRefreshToken())) {
			throw new JwtException(MALFORMED_JWT);
		}
		String newAccessToken = jwtTokenProvider.createAccessToken(id);
		String newRefreshToken = jwtTokenProvider.createRefreshToken(id);
		Long expiration = jwtTokenProvider.getExpiration(refreshToken);
		refreshTokenRepository.save(id, newRefreshToken, expiration);
		return TokenResponse.from(newAccessToken, newRefreshToken);
	}

	@Override
	@Transactional
	public void signOut(AuthInfo authInfo) {
		refreshTokenRepository.deleteByUserId(authInfo.getId());
	}
}

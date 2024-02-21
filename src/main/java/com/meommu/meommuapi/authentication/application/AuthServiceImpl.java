package com.meommu.meommuapi.authentication.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.authentication.dto.AuthInfo;
import com.meommu.meommuapi.authentication.dto.SignInRequest;
import com.meommu.meommuapi.authentication.exception.JwtException;
import com.meommu.meommuapi.authentication.exception.SignInFailedException;
import com.meommu.meommuapi.authentication.exception.errorCode.AuthErrorCode;
import com.meommu.meommuapi.authentication.infrastructure.RefreshTokenRepository;
import com.meommu.meommuapi.authentication.dto.ReissueRequest;
import com.meommu.meommuapi.authentication.dto.TokenResponse;
import com.meommu.meommuapi.authentication.configuration.JwtTokenProvider;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.kindergarten.infrastructure.KindergartenRepository;

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
			throw new JwtException(AuthErrorCode.MALFORMED_JWT);
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

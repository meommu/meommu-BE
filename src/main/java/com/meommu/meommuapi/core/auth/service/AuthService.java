package com.meommu.meommuapi.core.auth.service;

import static com.meommu.meommuapi.core.auth.exception.errorCode.AuthErrorCode.*;

import javax.naming.AuthenticationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.core.auth.dto.AuthInfo;
import com.meommu.meommuapi.core.auth.dto.ReissueRequest;
import com.meommu.meommuapi.core.auth.dto.SignInRequest;
import com.meommu.meommuapi.core.auth.dto.TokenResponse;
import com.meommu.meommuapi.core.auth.exception.AuthorizationException;
import com.meommu.meommuapi.core.auth.exception.JwtException;
import com.meommu.meommuapi.core.auth.exception.SignInFailedException;
import com.meommu.meommuapi.core.auth.token.JwtTokenProvider;
import com.meommu.meommuapi.core.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.core.kindergarten.repository.KindergartenRepository;
import com.meommu.meommuapi.global.infra.redis.util.RedisUtils;

import jakarta.security.auth.message.AuthException;

@Service
public class AuthService {

	private final KindergartenRepository kindergartenRepository;

	private final Encryptor encryptor;

	private final JwtTokenProvider jwtTokenProvider;

	private final RedisUtils redisUtils;

	public AuthService(KindergartenRepository kindergartenRepository, Encryptor encryptor,
		JwtTokenProvider jwtTokenProvider, RedisUtils redisUtils) {
		this.kindergartenRepository = kindergartenRepository;
		this.encryptor = encryptor;
		this.jwtTokenProvider = jwtTokenProvider;
		this.redisUtils = redisUtils;
	}

	@Transactional
	public TokenResponse signIn(SignInRequest request) {
		Kindergarten kindergarten = kindergartenRepository.findByEmailValueAndPasswordValue(request.getEmail(),
			encryptor.encrypt(request.getPassword())).orElseThrow(() -> new SignInFailedException());
		String accessToken = jwtTokenProvider.createAccessToken(kindergarten.getId());
		String refreshToken = jwtTokenProvider.createRefreshToken(kindergarten.getId());

		Long expiration = jwtTokenProvider.getExpiration(refreshToken);
		redisUtils.setRefreshToken(kindergarten.getId(), refreshToken, expiration);

		return TokenResponse.from(accessToken, refreshToken);
	}

	@Transactional
	public TokenResponse reissue(AuthInfo authInfo, ReissueRequest request) {
		// 1. Refresh Token 검증
		jwtTokenProvider.validateRefreshToken(request.getRefreshToken());

		// 2. 인증정보
		Long id = authInfo.getId();

		// 3. Redis RefreshToken 조회
		String refreshToken = redisUtils.getRefreshToken(id);
		if (!refreshToken.equals(request.getRefreshToken())) {
			throw new JwtException(MALFORMED_JWT);
		}

		// 3. 새로운 토큰 발급
		String newAccessToken = jwtTokenProvider.createAccessToken(id);
		String newRefreshToken = jwtTokenProvider.createRefreshToken(id);

		// 4. Redis RefreshToken 업데이트
		Long expiration = jwtTokenProvider.getExpiration(refreshToken);
		redisUtils.setRefreshToken(id, newRefreshToken, expiration);

		// 5. 토큰 반환
		return TokenResponse.from(newAccessToken, newRefreshToken);
	}

	@Transactional
	public void signOut(AuthInfo authInfo) {
		redisUtils.deleteRefreshToken(authInfo.getId());
	}
}

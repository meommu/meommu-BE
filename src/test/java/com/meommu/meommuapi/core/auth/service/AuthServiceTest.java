package com.meommu.meommuapi.core.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.meommu.meommuapi.core.auth.dto.AuthInfo;
import com.meommu.meommuapi.core.auth.dto.ReissueRequest;
import com.meommu.meommuapi.core.auth.dto.SignInRequest;
import com.meommu.meommuapi.core.auth.dto.TokenResponse;
import com.meommu.meommuapi.core.auth.exception.SignInFailedException;
import com.meommu.meommuapi.core.auth.exception.errorCode.AuthErrorCode;
import com.meommu.meommuapi.core.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.Password;
import com.meommu.meommuapi.util.ServiceTest;

class AuthServiceTest extends ServiceTest {

	Kindergarten kindergarten;

	AuthInfo authInfo;

	@BeforeEach
	void setUp() {
		kindergarten = Kindergarten.builder()
			.name("멈무유치원")
			.ownerName("김철수")
			.phone("010-0000-0000")
			.email("meommu@exam.com")
			.password(Password.of(encryptor, "Password1!"))
			.build();

		authInfo = new AuthInfo(1L);
	}

	@DisplayName("로그인을 하면 access token과 refresh token을 생성한다.")
	@Test
	void signIn() {
		// given
		var request = SignInRequest.builder()
			.email("meommu@exam.com")
			.password("Password1!")
			.build();
		given(kindergartenRepository.findByEmailValueAndPasswordValue(any(), any())).willReturn(
			Optional.ofNullable(kindergarten));
		given(jwtTokenProvider.createAccessToken(any())).willReturn("AccessToken");
		given(jwtTokenProvider.createRefreshToken(any())).willReturn("RefreshToken");

		// when
		TokenResponse tokenResponse = authService.signIn(request);

		// then
		Assertions.assertAll(
			() -> assertThat(tokenResponse.getAccessToken()).isEqualTo("AccessToken"),
			() -> assertThat(tokenResponse.getRefreshToken()).isEqualTo("RefreshToken")
		);
	}

	@DisplayName("잘못된 이메일이나 비밀번호로 로그인을 하면 실패한다.")
	@Test
	void signIn_exception_loginFailed() {
		// given
		var request = SignInRequest.builder()
			.email("meommu@exam.com")
			.password("Password123!")
			.build();

		// when & then
		assertThatThrownBy(() -> authService.signIn(request))
			.isInstanceOf(SignInFailedException.class)
			.hasMessageContaining(AuthErrorCode.SIGN_IN_FAILED.getDescription());
	}

	@DisplayName("토큰 재발급을 하면 access token과 refresh token을 재생성한다.")
	@Test
	void reissue() {
		// given
		var request = ReissueRequest.builder()
			.refreshToken("RefreshToken")
			.build();
		given(redisUtils.getRefreshToken(any())).willReturn("RefreshToken");
		given(jwtTokenProvider.createAccessToken(any())).willReturn("NewAccessToken");
		given(jwtTokenProvider.createRefreshToken(any())).willReturn("NewRefreshToken");
		given(jwtTokenProvider.getExpiration(any())).willReturn(999999999L);

		// when
		TokenResponse tokenResponse = authService.reissue(authInfo, request);

		// then
		Assertions.assertAll(
			() -> assertThat(tokenResponse.getAccessToken()).isEqualTo("NewAccessToken"),
			() -> assertThat(tokenResponse.getRefreshToken()).isEqualTo("NewRefreshToken")
		);
	}

	@DisplayName("로그아웃이 정상적으로 수행된다.")
	@Test
	void signOut() {
		// when & then
		assertThatNoException()
			.isThrownBy(() -> authService.signOut(authInfo));
	}
}
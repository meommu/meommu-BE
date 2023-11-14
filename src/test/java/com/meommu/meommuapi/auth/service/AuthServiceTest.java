package com.meommu.meommuapi.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.meommu.meommuapi.auth.dto.SignInRequest;
import com.meommu.meommuapi.auth.dto.TokenResponse;
import com.meommu.meommuapi.auth.exception.SignInFailedException;
import com.meommu.meommuapi.auth.exception.errorCode.AuthErrorCode;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.kindergarten.domain.embedded.Password;
import com.meommu.meommuapi.util.ServiceTest;

class AuthServiceTest extends ServiceTest {

	Kindergarten kindergarten;

	@BeforeEach
	void setUp() {
		kindergarten = Kindergarten.builder()
			.name("멈무유치원")
			.ownerName("김철수")
			.phone("010-0000-0000")
			.email("meommu@exam.com")
			.password(Password.of(encryptor, "Password1!"))
			.build();
	}

	@DisplayName("로그인을 하면 access token을 생성한다.")
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

		// when
		TokenResponse tokenResponse = authService.signIn(request);

		// then
		assertThat(tokenResponse.getAccessToken()).isEqualTo("AccessToken");
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
}
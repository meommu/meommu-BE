package com.meommu.meommuapi.kindergarten.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.meommu.meommuapi.authentication.dto.AuthInfo;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.kindergarten.domain.embedded.Password;
import com.meommu.meommuapi.kindergarten.dto.EmailRequest;
import com.meommu.meommuapi.kindergarten.dto.KindergartenResponse;
import com.meommu.meommuapi.kindergarten.dto.KindergartenUpdateRequest;
import com.meommu.meommuapi.kindergarten.dto.MyInfoResponse;
import com.meommu.meommuapi.kindergarten.dto.SignUpRequest;
import com.meommu.meommuapi.kindergarten.exception.DuplicateEmailException;
import com.meommu.meommuapi.kindergarten.exception.InvalidPasswordConfirmationException;
import com.meommu.meommuapi.kindergarten.exception.KindergartenNotFoundException;
import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.util.ServiceTest;

class KindergartenServiceTest extends ServiceTest {

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

	@DisplayName("회원가입을 한다.")
	@Test
	void signUp() {
		// given
		var signUpRequest = SignUpRequest.builder()
			.name("멈무유치원")
			.ownerName("김철수")
			.phone("010-0000-0000")
			.email("meommu@exam.com")
			.password("Password1!")
			.passwordConfirmation("Password1!")
			.build();
		given(kindergartenRepository.save(any())).willReturn(kindergarten);

		// when & then
		assertThatNoException()
			.isThrownBy(() -> kindergartenService.signUp(signUpRequest));
	}

	@DisplayName("아이디와 비밀번호가 다르면 회원가입에 실패한다.")
	@Test
	void signUp_exception_InvalidPasswordConfirmation() {
		// given
		var signUpRequest = SignUpRequest.builder()
			.name("멈무유치원")
			.ownerName("김철수")
			.phone("010-0000-0000")
			.email("meommu@exam.com")
			.password("Password1!")
			.passwordConfirmation("Password123!")
			.build();

		// when & then
		assertThatThrownBy(() -> kindergartenService.signUp(signUpRequest))
			.isInstanceOf(InvalidPasswordConfirmationException.class)
			.hasMessageContaining(KindergartenErrorCode.INVALID_PASSWORD_CONFIRMATION.getDescription());
	}

	@DisplayName("이미 존재하는 이메일이면 회원가입에 실패한다.")
	@Test
	void signUp_exception_DuplicateEmail() {
		// given
		var signUpRequest = SignUpRequest.builder()
			.name("멈무유치원")
			.ownerName("김철수")
			.phone("010-0000-0000")
			.email("meommu@exam.com")
			.password("Password1!")
			.passwordConfirmation("Password1!")
			.build();
		given(kindergartenRepository.existsByEmailValue(any())).willReturn(true);

		// when & then
		assertThatThrownBy(() -> kindergartenService.signUp(signUpRequest))
			.isInstanceOf(DuplicateEmailException.class)
			.hasMessageContaining(KindergartenErrorCode.DUPLICATED_EMAIL.getDescription());
	}

	@DisplayName("이메일 중복을 확인한다. -> 중복이 아닐 경우")
	@Test
	void existsByEmail_false() {
		// given
		var emailRequest = EmailRequest.builder()
			.email("meommu@exam.com")
			.build();
		given(kindergartenRepository.existsByEmailValue(any())).willReturn(false);

		// when & then
		assertThatNoException()
			.isThrownBy(() -> kindergartenService.existsByEmail(emailRequest));
	}

	@DisplayName("이메일 중복을 확인한다. -> 중복일 경우")
	@Test
	void existsByEmail_true() {
		// given
		var emailRequest = EmailRequest.builder()
			.email("meommu@exam.com")
			.build();
		given(kindergartenRepository.existsByEmailValue(any())).willReturn(true);

		// when & then
		assertThatNoException()
			.isThrownBy(() -> kindergartenService.existsByEmail(emailRequest));
	}

	@DisplayName("토큰으로부터 로그인한 회원의 정보를 조회한다.")
	@Test
	void findMyInfo() {
		// given
		var authInfo = new AuthInfo(1L);
		given(kindergartenRepository.findById(any())).willReturn(Optional.of(kindergarten));

		// when
		MyInfoResponse myInfoResponse = kindergartenService.findMyInfo(authInfo);

		// then
		assertThat(myInfoResponse.getEmail()).isEqualTo("meommu@exam.com");
	}

	@DisplayName("잘못된 id가 있는 토큰으로부터 로그인한 회원의 정보를 조회시 실패한다.")
	@Test
	void findMyInfo_exception_invalidId() {
		// given
		var authInfo = new AuthInfo(99999999L);

		// when & then
		assertThatThrownBy(() -> kindergartenService.findMyInfo(authInfo))
			.isInstanceOf(KindergartenNotFoundException.class)
			.hasMessageContaining("유치원(id = 99999999)를 찾을 수 없습니다.");
	}

	@DisplayName("회원 정보를 수정한다.")
	@Test
	void update() {
		// given
		var authInfo = new AuthInfo(1L);
		var kindergartenUpdateRequest = KindergartenUpdateRequest.builder()
			.name("수정된 멈무유치원")
			.ownerName("수정된 김철수")
			.phone("010-9999-9999")
			.build();
		given(kindergartenRepository.findById(any())).willReturn(Optional.of(kindergarten));

		// when
		kindergartenService.update(kindergartenUpdateRequest, authInfo);
		KindergartenResponse updatedKindergartenResponse = kindergartenService.find(authInfo);

		// then
		assertAll(
			() -> assertThat(updatedKindergartenResponse.getName()).isEqualTo("수정된 멈무유치원"),
			() -> assertThat(updatedKindergartenResponse.getOwnerName()).isEqualTo("수정된 김철수"),
			() -> assertThat(updatedKindergartenResponse.getPhone()).isEqualTo("010-9999-9999")
		);
	}

	@DisplayName("회원 탈퇴한다.")
	@Test
	void delete() {
		// given
		var authInfo = new AuthInfo(1L);
		given(kindergartenRepository.findById(any())).willReturn(Optional.of(kindergarten));

		// when & then
		assertThatNoException()
			.isThrownBy(() -> kindergartenService.delete(authInfo));
	}
}

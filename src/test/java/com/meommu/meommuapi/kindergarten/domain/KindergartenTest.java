package com.meommu.meommuapi.kindergarten.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.meommu.meommuapi.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.kindergarten.domain.embedded.Password;
import com.meommu.meommuapi.kindergarten.exception.InvalidEmailFormatException;
import com.meommu.meommuapi.kindergarten.exception.InvalidNameFormatException;
import com.meommu.meommuapi.kindergarten.exception.InvalidOwnerNameFormatException;
import com.meommu.meommuapi.kindergarten.exception.InvalidPasswordFormatException;
import com.meommu.meommuapi.kindergarten.exception.InvalidPhoneFormatException;
import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;

class KindergartenTest {

	Encryptor encryptor;

	@BeforeEach
	void setUp() {
		encryptor = new Encryptor();
	}

	@DisplayName("회원을 생성할 수 있다.")
	@Test
	void createKindergarten() {
		// when
		var kindergarten = Kindergarten.of(
			"멈무유치원",
			"김철수",
			"010-0000-0000",
			"meommu@exam.com",
			Password.of(encryptor, "Password1!"));

		// then
		assertAll(
			() -> assertThat(kindergarten.getName()).isEqualTo("멈무유치원"),
			() -> assertThat(kindergarten.getOwnerName()).isEqualTo("김철수"),
			() -> assertThat(kindergarten.getPhone()).isEqualTo("010-0000-0000"),
			() -> assertThat(kindergarten.getEmail()).isEqualTo("meommu@exam.com"),
			() -> assertThat(kindergarten.getPassword()).isEqualTo(encryptor.encrypt("Password1!"))
		);
	}

	@DisplayName("잘못된 이메일 형식이 입력되면 예외가 발생한다.")
	@ParameterizedTest
	@EmptySource
	@ValueSource(strings = {"invalidexam.com", " valid@exam.com", "valid@exam .com"})
	void createKindergarten_exception_invalidEmailFormat(String invalidEmail) {
		// when & then
		assertThatThrownBy(
			() -> Kindergarten.of(
				"멈무유치원",
				"김철수",
				"010-0000-0000",
				invalidEmail,
				Password.of(encryptor, "Password1!")))
			.isInstanceOf(InvalidEmailFormatException.class)
			.hasMessageContaining(KindergartenErrorCode.INVALID_EMAIL_FORMAT.getDescription());
	}

	@DisplayName("잘못된 비밀번호 형식이 입력되면 예외가 발생한다.")
	@ParameterizedTest
	@EmptySource
	@ValueSource(strings = {"Password1", "password!", "password", " Password", "P1!",
		"PasswordPasswordPasswordPassword1!"})
	void createKindergarten_exception_invalidPasswordFormat(String invalidPassword) {
		// when & then
		assertThatThrownBy(
			() -> Kindergarten.of(
				"멈무유치원",
				"김철수",
				"010-0000-0000",
				"meommu@exam.com",
				Password.of(encryptor, invalidPassword)))
			.isInstanceOf(InvalidPasswordFormatException.class)
			.hasMessageContaining(KindergartenErrorCode.INVALID_PASSWORD_FORMAT.getDescription());
	}

	@DisplayName("잘못된 유치원 이름 형식이 입력되면 예외가 발생한다.")
	@ParameterizedTest
	@EmptySource
	@ValueSource(strings = {"김수한무거북이와두루미삼천갑자동방삭", "김", " ",})
	void createKindergarten_exception_invalidNameFormat(String invalidName) {
		// when & then
		assertThatThrownBy(
			() -> Kindergarten.of(
				invalidName,
				"김철수",
				"010-0000-0000",
				"meommu@exam.com",
				Password.of(encryptor, "Password1!")))
			.isInstanceOf(InvalidNameFormatException.class)
			.hasMessageContaining(KindergartenErrorCode.INVALID_NAME_FORMAT.getDescription());
	}

	@DisplayName("잘못된 유치원 대표자 이름 형식이 입력되면 예외가 발생한다.")
	@ParameterizedTest
	@EmptySource
	@ValueSource(strings = {"김수한무거북이와두루미삼천갑자동방삭", "김", " "})
	void createKindergarten_exception_invalidOwnerNameFormat(String invalidOwnerName) {
		// when & then
		assertThatThrownBy(
			() -> Kindergarten.of(
				"멈무유치원",
				invalidOwnerName,
				"010-0000-0000",
				"meommu@exam.com",
				Password.of(encryptor, "Password1!")))
			.isInstanceOf(InvalidOwnerNameFormatException.class)
			.hasMessageContaining(KindergartenErrorCode.INVALID_OWNER_NAME_FORMAT.getDescription());
	}

	@DisplayName("잘못된 전화번호 형식이 입력되면 예외가 발생한다.")
	@ParameterizedTest
	@EmptySource
	@ValueSource(strings = {"0000-0000-0000", "01000000000", "invalidValue", " 010-0000-0000"})
	void createKindergarten_exception_invalidPhoneFormat(String invalidPhone) {
		// when & then
		assertThatThrownBy(
			() -> Kindergarten.of(
				"멈무유치원",
				"김철수",
				invalidPhone,
				"meommu@exam.com",
				Password.of(encryptor, "Password1!")))
			.isInstanceOf(InvalidPhoneFormatException.class)
			.hasMessageContaining(KindergartenErrorCode.INVALID_PHONE_FORMAT.getDescription());
	}
}
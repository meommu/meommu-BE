package com.meommu.meommuapi.core.kindergarten.domain.embedded;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.meommu.meommuapi.core.kindergarten.domain.embedded.Encryptor;

class EncryptorTest {

	Encryptor encryptor;

	@BeforeEach
	void setUp() {
		encryptor = new Encryptor();
	}

	@DisplayName("비밀번호를 암호화하면 64글자가 된다.")
	@Test
	void encrypt() {
		// given
		var password = "Password1!";

		// when
		var encryptedPassword = encryptor.encrypt(password);

		// then
		assertAll(
			() -> assertThat(encryptedPassword.length()).isEqualTo(64),
			() -> assertThat(encryptedPassword).isNotEqualTo(encryptor.encrypt("Password123!"))
		);
	}
}
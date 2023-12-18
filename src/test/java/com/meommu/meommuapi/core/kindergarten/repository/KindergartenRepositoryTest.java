package com.meommu.meommuapi.core.kindergarten.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.meommu.meommuapi.core.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.Password;
import com.meommu.meommuapi.util.RepositoryTest;

class KindergartenRepositoryTest extends RepositoryTest {

	Kindergarten kindergarten;

	@BeforeEach
	void setUp() {
		kindergarten = Kindergarten.of(
			"멈무유치원",
			"김철수",
			"010-0000-0000",
			"meommu@exam.com",
			Password.of(encryptor, "Password1!"));
		kindergartenRepository.save(kindergarten);
	}

	@DisplayName("동일한 이메일이 저장되어 있다면 true를 반환한다.")
	@Test
	void existsByEmailValue() {
		//when
		boolean exists = kindergartenRepository.existsByEmailValue("meommu@exam.com");

		// then
		assertThat(exists).isTrue();
	}

	@DisplayName("이메일과 비밀번호로 회원을 찾을 수 있다.")
	@Test
	void findByEmailValueAndPasswordValue() {
		// when
		Kindergarten foundedkindergarten = kindergartenRepository.findByEmailValueAndPasswordValue(
			"meommu@exam.com", encryptor.encrypt("Password1!")).get();

		assertAll(
			() -> assertThat(foundedkindergarten.getEmail()).isEqualTo("meommu@exam.com")
		);
	}
}
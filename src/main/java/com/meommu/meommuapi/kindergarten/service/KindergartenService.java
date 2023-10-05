package com.meommu.meommuapi.kindergarten.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.auth.dto.AuthInfo;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.kindergarten.domain.embedded.Password;
import com.meommu.meommuapi.kindergarten.dto.EmailRequest;
import com.meommu.meommuapi.kindergarten.dto.SignUpRequest;
import com.meommu.meommuapi.kindergarten.exception.DuplicateEmailException;
import com.meommu.meommuapi.kindergarten.exception.InvalidPasswordConfirmationException;
import com.meommu.meommuapi.kindergarten.exception.KindergartenNotFoundException;
import com.meommu.meommuapi.kindergarten.repository.KindergartenRepository;

@Transactional(readOnly = true)
@Service
public class KindergartenService {

	private final KindergartenRepository kindergartenRepository;

	private final Encryptor encryptor;

	public KindergartenService(KindergartenRepository kindergartenRepository, Encryptor encryptor) {
		this.kindergartenRepository = kindergartenRepository;
		this.encryptor = encryptor;
	}

	@Transactional
	public void signUp(SignUpRequest signUpRequest) {
		validate(signUpRequest);
		Kindergarten kindergarten = Kindergarten.builder()
			.name(signUpRequest.getName())
			.ownerName(signUpRequest.getOwnerName())
			.phone(signUpRequest.getPhone())
			.email(signUpRequest.getEmail())
			.password(Password.of(encryptor, signUpRequest.getPassword()))
			.build();
		kindergartenRepository.save(kindergarten);
	}

	public void existsByEmail(EmailRequest emailRequest) {
		String email = emailRequest.getEmail();
		validateUniqueEmail(email);
	}

	private void validate(SignUpRequest signUpRequest) {
		confirmPassword(signUpRequest.getPassword(), signUpRequest.getPasswordConfirmation());
		validateUniqueEmail(signUpRequest.getEmail());
	}

	private void confirmPassword(String password, String passwordConfirmation) {
		if (!Objects.equals(password, passwordConfirmation)) {
			throw new InvalidPasswordConfirmationException();
		}
	}

	private void validateUniqueEmail(String email) {
		if (kindergartenRepository.existsByEmailValue(email)) {
			throw new DuplicateEmailException();
		}
	}
}

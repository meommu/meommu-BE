package com.meommu.meommuapi.kindergarten.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.auth.dto.AuthInfo;
import com.meommu.meommuapi.auth.exception.AuthorizationException;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.kindergarten.domain.embedded.Password;
import com.meommu.meommuapi.kindergarten.dto.EmailRequest;
import com.meommu.meommuapi.kindergarten.dto.MyInfoResponse;
import com.meommu.meommuapi.kindergarten.dto.KindergartenResponse;
import com.meommu.meommuapi.kindergarten.dto.KindergartenUpdateRequest;
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
	public MyInfoResponse signUp(SignUpRequest signUpRequest) {
		validate(signUpRequest);
		Kindergarten kindergarten = Kindergarten.of(
			signUpRequest.getName(),
			signUpRequest.getOwnerName(),
			signUpRequest.getPhone(),
			signUpRequest.getEmail(),
			Password.of(encryptor, signUpRequest.getPassword())
		);
		kindergartenRepository.save(kindergarten);
		return MyInfoResponse.from(kindergarten);
	}

	public boolean existsByEmail(EmailRequest emailRequest) {
		String email = emailRequest.getEmail();
		return isEmailUnique(email);
	}

	public MyInfoResponse findMyInfo(AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		return MyInfoResponse.from(kindergarten);
	}

	public KindergartenResponse find(Long kindergartenId, AuthInfo authInfo) {
		if (kindergartenId != authInfo.getId()) {
			throw new AuthorizationException();
		}
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		return KindergartenResponse.from(kindergarten);
	}

	public void update(Long kindergartenId, KindergartenUpdateRequest myInfoUpdateRequest, AuthInfo authInfo) {
		if (kindergartenId != authInfo.getId()) {
			throw new AuthorizationException();
		}
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		kindergarten.updateName(myInfoUpdateRequest.getName());
		kindergarten.updateOwnerName(myInfoUpdateRequest.getOwnerName());
		kindergarten.updatePhone(myInfoUpdateRequest.getPhone());
	}

	public void delete(Long kindergartenId, AuthInfo authInfo) {
		if (kindergartenId != authInfo.getId()) {
			throw new AuthorizationException();
		}
		kindergartenRepository.deleteById(kindergartenId);
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

	private boolean isEmailUnique(String email) {
		return !kindergartenRepository.existsByEmailValue(email);
	}

	private void validateUniqueEmail(String email) {
		if (kindergartenRepository.existsByEmailValue(email)) {
			throw new DuplicateEmailException();
		}
	}

	private Kindergarten getKindergartenById(Long id) {
		return kindergartenRepository.findById(id).orElseThrow(() -> new KindergartenNotFoundException(id));
	}

}

package com.meommu.meommuapi.core.kindergarten.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.core.auth.dto.AuthInfo;
import com.meommu.meommuapi.core.auth.exception.AuthorizationException;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.Password;
import com.meommu.meommuapi.core.kindergarten.dto.KindergartenPasswordUpdateRequest;
import com.meommu.meommuapi.core.kindergarten.exception.DuplicateEmailException;
import com.meommu.meommuapi.core.kindergarten.exception.EmailCodeInvalidException;
import com.meommu.meommuapi.core.kindergarten.exception.EmailNotFoundException;
import com.meommu.meommuapi.core.kindergarten.exception.InvalidPasswordConfirmationException;
import com.meommu.meommuapi.core.kindergarten.exception.KindergartenNotFoundException;
import com.meommu.meommuapi.core.kindergarten.repository.KindergartenRepository;
import com.meommu.meommuapi.core.mail.service.MailService;
import com.meommu.meommuapi.global.infra.redis.util.RedisUtils;
import com.meommu.meommuapi.global.util.Utils;
import com.meommu.meommuapi.core.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.core.kindergarten.dto.EmailRequest;
import com.meommu.meommuapi.core.kindergarten.dto.MyInfoResponse;
import com.meommu.meommuapi.core.kindergarten.dto.KindergartenResponse;
import com.meommu.meommuapi.core.kindergarten.dto.KindergartenUpdateRequest;
import com.meommu.meommuapi.core.kindergarten.dto.SignUpRequest;

@Transactional(readOnly = true)
@Service
public class KindergartenService {

	private final KindergartenRepository kindergartenRepository;

	private final MailService mailService;

	private final RedisUtils redisUtils;

	private final Encryptor encryptor;

	@Value("${spring.mail.auth-code-expiration-millis}")
	private long authCodeExpirationMillis;

	public KindergartenService(KindergartenRepository kindergartenRepository,
		MailService mailService, RedisUtils redisUtils, Encryptor encryptor) {
		this.kindergartenRepository = kindergartenRepository;
		this.mailService = mailService;
		this.redisUtils = redisUtils;
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

	public KindergartenResponse find(AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		return KindergartenResponse.from(kindergarten);
	}

	@Transactional
	public void update(KindergartenUpdateRequest kindergartenUpdateRequest, AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		kindergarten.updateName(kindergartenUpdateRequest.getName());
		kindergarten.updateOwnerName(kindergartenUpdateRequest.getOwnerName());
		kindergarten.updatePhone(kindergartenUpdateRequest.getPhone());
	}

	@Transactional
	public void delete(AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		kindergartenRepository.delete(kindergarten);
	}

	@Transactional
	public void sendCodeToEmail(String email) {
		if (isEmailUnique(email)) {
			throw new EmailNotFoundException();
		}
		String title = "meommu 이메일 인증 메일";
		String code = Utils.createCode(6);
		redisUtils.setEmailCode(email, code, authCodeExpirationMillis);
		mailService.sendEmail(email, title, code);
	}

	@Transactional
	public boolean verifiedCode(String email, String code) {
		if (isEmailUnique(email)) {
			throw new EmailNotFoundException();
		}
		if (!redisUtils.getEmailCode(email).equals(code)) {
			throw new EmailCodeInvalidException();
		}
		redisUtils.deleteEmailCode(email);
		return true;
	}

	@Transactional
	public void updatePassword(String email, KindergartenPasswordUpdateRequest request) {
		Kindergarten kindergarten = getKindergartenByEmail(email);
		confirmPassword(request.getPassword(), request.getPasswordConfirmation());
		kindergarten.updatePassword(encryptor, request.getPassword());
	}

	private void validate(SignUpRequest signUpRequest) {
		confirmPassword(signUpRequest.getPassword(), signUpRequest.getPasswordConfirmation());
		validateUniqueEmail(signUpRequest.getEmail());
	}

	private void validateOwner(Long authId, Long kindergartenId) {
		if (authId != kindergartenId) {
			throw new AuthorizationException();
		}
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

	private Kindergarten getKindergartenByEmail(String email) {
		return kindergartenRepository.findByEmailValue(email).orElseThrow(() -> new KindergartenNotFoundException());
	}
}

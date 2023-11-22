package com.meommu.meommuapi.kindergarten.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.auth.dto.AuthInfo;
import com.meommu.meommuapi.auth.exception.AuthorizationException;
import com.meommu.meommuapi.common.util.Utils;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.kindergarten.domain.MailVerificationCode;
import com.meommu.meommuapi.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.kindergarten.domain.embedded.Password;
import com.meommu.meommuapi.kindergarten.dto.EmailRequest;
import com.meommu.meommuapi.kindergarten.dto.KindergartenPasswordUpdateRequest;
import com.meommu.meommuapi.kindergarten.dto.MyInfoResponse;
import com.meommu.meommuapi.kindergarten.dto.KindergartenResponse;
import com.meommu.meommuapi.kindergarten.dto.KindergartenUpdateRequest;
import com.meommu.meommuapi.kindergarten.dto.SignUpRequest;
import com.meommu.meommuapi.kindergarten.exception.DuplicateEmailException;
import com.meommu.meommuapi.kindergarten.exception.EmailCodeExpiredException;
import com.meommu.meommuapi.kindergarten.exception.EmailCodeInvalidException;
import com.meommu.meommuapi.kindergarten.exception.EmailNotFoundException;
import com.meommu.meommuapi.kindergarten.exception.InvalidPasswordConfirmationException;
import com.meommu.meommuapi.kindergarten.exception.KindergartenNotFoundException;
import com.meommu.meommuapi.kindergarten.repository.KindergartenRepository;
import com.meommu.meommuapi.kindergarten.repository.MailVerificationCodeRepository;
import com.meommu.meommuapi.mail.service.MailService;

@Transactional(readOnly = true)
@Service
public class KindergartenService {

	private final KindergartenRepository kindergartenRepository;

	private final MailVerificationCodeRepository mailVerificationCodeRepository;

	private final MailService mailService;

	private final Encryptor encryptor;

	@Value("${spring.mail.auth-code-expiration-millis}")
	private long authCodeExpirationMillis;

	public KindergartenService(KindergartenRepository kindergartenRepository,
		MailVerificationCodeRepository mailVerificationCodeRepository, MailService mailService,
		Encryptor encryptor) {
		this.kindergartenRepository = kindergartenRepository;
		this.mailVerificationCodeRepository = mailVerificationCodeRepository;
		this.mailService = mailService;
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
		validateOwner(authInfo.getId(), kindergartenId);
		Kindergarten kindergarten = getKindergartenById(kindergartenId);
		return KindergartenResponse.from(kindergarten);
	}

	@Transactional
	public void update(Long kindergartenId, KindergartenUpdateRequest kindergartenUpdateRequest, AuthInfo authInfo) {
		validateOwner(authInfo.getId(), kindergartenId);
		Kindergarten kindergarten = getKindergartenById(kindergartenId);
		kindergarten.updateName(kindergartenUpdateRequest.getName());
		kindergarten.updateOwnerName(kindergartenUpdateRequest.getOwnerName());
		kindergarten.updatePhone(kindergartenUpdateRequest.getPhone());
	}

	@Transactional
	public void delete(Long kindergartenId, AuthInfo authInfo) {
		validateOwner(authInfo.getId(), kindergartenId);
		Kindergarten kindergarten = getKindergartenById(kindergartenId);
		kindergartenRepository.delete(kindergarten);
	}

	@Transactional
	public void sendCodeToEmail(String email) {
		if (isEmailUnique(email)) {
			throw new EmailNotFoundException();
		}
		String title = "meommu 이메일 인증 메일";
		String code = Utils.createCode(6);
		LocalDateTime expirationTime = LocalDateTime.now().plus(authCodeExpirationMillis, ChronoUnit.MILLIS);
		MailVerificationCode mailVerificationCode = MailVerificationCode.of(email, code, expirationTime);
		mailVerificationCodeRepository.save(mailVerificationCode); // TODO Redis에 만료기간 저장
		mailService.sendEmail(email, title, code);
	}

	@Transactional
	public boolean verifiedCode(String email, String code) {
		if (isEmailUnique(email)) {
			throw new EmailNotFoundException();
		}
		MailVerificationCode mailVerificationCode = mailVerificationCodeRepository.findByEmailAndCode(email, code)
			.orElseThrow(() -> new EmailCodeInvalidException());
		if (mailVerificationCode.isExpired()) {
			throw new EmailCodeExpiredException();
		}
		mailVerificationCodeRepository.delete(mailVerificationCode);
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

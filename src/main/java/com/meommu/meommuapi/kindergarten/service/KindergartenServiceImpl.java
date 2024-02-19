package com.meommu.meommuapi.kindergarten.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import com.meommu.meommuapi.auth.dto.AuthInfo;
import com.meommu.meommuapi.auth.exception.AuthorizationException;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.kindergarten.domain.embedded.Password;
import com.meommu.meommuapi.kindergarten.dto.EmailRequest;
import com.meommu.meommuapi.kindergarten.dto.KindergartenPasswordUpdateRequest;
import com.meommu.meommuapi.kindergarten.dto.KindergartenResponse;
import com.meommu.meommuapi.kindergarten.dto.KindergartenUpdateRequest;
import com.meommu.meommuapi.kindergarten.dto.MyInfoResponse;
import com.meommu.meommuapi.kindergarten.dto.SignUpRequest;
import com.meommu.meommuapi.kindergarten.exception.DuplicateEmailException;
import com.meommu.meommuapi.kindergarten.exception.EmailCodeInvalidException;
import com.meommu.meommuapi.kindergarten.exception.EmailNotFoundException;
import com.meommu.meommuapi.kindergarten.exception.InvalidPasswordConfirmationException;
import com.meommu.meommuapi.kindergarten.exception.KindergartenNotFoundException;
import com.meommu.meommuapi.kindergarten.repository.EmailCodeRepository;
import com.meommu.meommuapi.kindergarten.repository.KindergartenRepository;
import com.meommu.meommuapi.mail.service.MailService;
import com.meommu.meommuapi.core.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@Service
public class KindergartenServiceImpl implements KindergartenService {

	private final KindergartenRepository kindergartenRepository;

	private final MailService mailService;

	private final EmailCodeRepository emailCodeRepository;

	private final Encryptor encryptor;

	private final ApplicationEventPublisher eventPublisher;

	@Value("${spring.mail.auth-code-expiration-millis}")
	private long authCodeExpirationMillis;

	public KindergartenServiceImpl(KindergartenRepository kindergartenRepository,
		MailService mailService, EmailCodeRepository emailCodeRepository, Encryptor encryptor,
		ApplicationEventPublisher eventPublisher) {
		this.kindergartenRepository = kindergartenRepository;
		this.mailService = mailService;
		this.emailCodeRepository = emailCodeRepository;
		this.encryptor = encryptor;
		this.eventPublisher = eventPublisher;
	}

	@Override
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

	@Override
	public boolean existsByEmail(EmailRequest emailRequest) {
		String email = emailRequest.getEmail();
		return isEmailUnique(email);
	}

	@Override
	public MyInfoResponse findMyInfo(AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		return MyInfoResponse.from(kindergarten);
	}

	@Override
	public KindergartenResponse find(AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		return KindergartenResponse.from(kindergarten);
	}

	@Override
	@Transactional
	public void update(KindergartenUpdateRequest kindergartenUpdateRequest, AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		kindergarten.updateName(kindergartenUpdateRequest.getName());
		kindergarten.updateOwnerName(kindergartenUpdateRequest.getOwnerName());
		kindergarten.updatePhone(kindergartenUpdateRequest.getPhone());
	}

	@Override
	@Transactional
	public void delete(AuthInfo authInfo) {
		Kindergarten kindergarten = getKindergartenById(authInfo.getId());
		kindergartenRepository.delete(kindergarten);
	}

	@Override
	@Transactional
	public void sendCodeToEmail(String email) {
		StopWatch s = new StopWatch();
		s.start();
		log.info("[time] {}" , s.getTotalTimeMillis());
		if (isEmailUnique(email)) {
			throw new EmailNotFoundException();
		}
		String title = "meommu 이메일 인증 메일";
		String code = Utils.createCode(6);
		emailCodeRepository.save(email, code, authCodeExpirationMillis);
		s.stop();
		log.info("[time] {}" , s.getTotalTimeMillis());
		eventPublisher.publishEvent(new PasswordFindEvent(email, title, code));
		// mailService.sendEmail(email, title, code);
	}

	@Override
	@Transactional
	public boolean verifiedCode(String email, String code) {
		if (isEmailUnique(email)) {
			throw new EmailNotFoundException();
		}
		if (!emailCodeRepository.findByEmail(email).equals(code)) {
			throw new EmailCodeInvalidException();
		}
		emailCodeRepository.deleteByEmail(email);
		return true;
	}

	@Override
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

package com.meommu.meommuapi.core.kindergarten.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meommu.meommuapi.core.kindergarten.domain.MailVerificationCode;

@Repository
public interface MailVerificationCodeRepository extends JpaRepository<MailVerificationCode, Long> {
	Optional<MailVerificationCode> findByEmailAndCode(String email, String code);
}

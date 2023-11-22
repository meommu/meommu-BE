package com.meommu.meommuapi.kindergarten.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.meommu.meommuapi.common.domain.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Entity
public class MailVerificationCode extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String email;

	@NotNull
	private String code;

	@NotNull
	private LocalDateTime expirationTime;

	protected MailVerificationCode() {
	}

	@Builder
	private MailVerificationCode(String email, String code, LocalDateTime expirationTime) {
		this.email = email;
		this.code = code;
		this.expirationTime = expirationTime;
	}

	public static MailVerificationCode of(String email, String code, LocalDateTime expirationTime) {
		return MailVerificationCode.builder()
			.email(email)
			.code(code)
			.expirationTime(expirationTime)
			.build();
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getCode() {
		return code;
	}

	public LocalDateTime getExpirationTime() {
		return expirationTime;
	}

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expirationTime);
	}
}

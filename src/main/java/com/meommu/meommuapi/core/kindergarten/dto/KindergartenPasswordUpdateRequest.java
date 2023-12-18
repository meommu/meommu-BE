package com.meommu.meommuapi.core.kindergarten.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class KindergartenPasswordUpdateRequest {

	@NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
	private String password;

	@NotBlank(message = "비밀번호 확인은 반드시 입력해야 합니다.")
	private String passwordConfirmation;

	private KindergartenPasswordUpdateRequest() {
	}

	@Builder
	private KindergartenPasswordUpdateRequest(String password, String passwordConfirmation) {
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
	}
}

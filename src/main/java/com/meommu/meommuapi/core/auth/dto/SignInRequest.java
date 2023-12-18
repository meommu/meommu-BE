package com.meommu.meommuapi.core.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignInRequest {

	@NotBlank(message = "이메일은 반드시 입력해야 합니다.")
	private String email;

	@NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
	private String password;

	private SignInRequest() {
	}

	@Builder
	private SignInRequest(final String email, final String password) {
		this.email = email;
		this.password = password;
	}
}
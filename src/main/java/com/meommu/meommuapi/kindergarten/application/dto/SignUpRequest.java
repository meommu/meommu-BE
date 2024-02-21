package com.meommu.meommuapi.kindergarten.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpRequest {

	@NotBlank(message = "유치원 이름은 반드시 입력해야 합니다.")
	private String name;

	@NotBlank(message = "유치원 대표자명은 반드시 입력해야 합니다.")
	private String ownerName;

	@NotBlank(message = "전화번호는 반드시 입력해야 합니다.")
	private String phone;

	@NotBlank(message = "이메일은 반드시 입력해야 합니다.")
	private String email;

	@NotBlank(message = "비밀번호는 반드시 입력해야 합니다.")
	private String password;

	@NotBlank(message = "비밀번호 확인은 반드시 입력해야 합니다.")
	private String passwordConfirmation;

	private SignUpRequest() {
	}

	@Builder
	private SignUpRequest(String name, String ownerName, String phone, String email, String password,
		String passwordConfirmation) {
		this.name = name;
		this.ownerName = ownerName;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
	}
}

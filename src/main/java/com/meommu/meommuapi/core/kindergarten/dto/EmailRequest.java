package com.meommu.meommuapi.core.kindergarten.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailRequest {

	@NotBlank(message = "이메일은 반드시 입력해야 합니다.")
	private String email;

	@Builder
	private EmailRequest(String email) {
		this.email = email;
	}
}

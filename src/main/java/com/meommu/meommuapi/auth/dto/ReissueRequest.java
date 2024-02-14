package com.meommu.meommuapi.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReissueRequest {

	@NotBlank(message = "refreshToken은 반드시 입력해야 합니다.")
	private String refreshToken;

	private ReissueRequest() {
	}

	@Builder
	public ReissueRequest(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}

package com.meommu.meommuapi.auth.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponse {

	private String accessToken;

	private String refreshToken;

	private TokenResponse() {
	}

	@Builder
	private TokenResponse(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public static TokenResponse from(String accessToken, String refreshToken) {
		return new TokenResponse(accessToken, refreshToken);
	}

}

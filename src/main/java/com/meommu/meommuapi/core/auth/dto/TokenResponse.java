package com.meommu.meommuapi.core.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponse {

	private String accessToken;

	private TokenResponse() {
	}

	@Builder
	private TokenResponse(String accessToken) {
		this.accessToken = accessToken;
	}

	public static TokenResponse from(String accessToken) {
		return new TokenResponse(accessToken);
	}

}

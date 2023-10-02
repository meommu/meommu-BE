package com.meommu.meommuapi.auth.token;

import org.springframework.http.HttpHeaders;

import jakarta.servlet.http.HttpServletRequest;

public class JwtTokenExtractor {

	private static final String BEARER_PREFIX = "Bearer";

	public static String extractAccessToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader.startsWith(BEARER_PREFIX)) {
			return authorizationHeader.substring(BEARER_PREFIX.length()).trim();
		}
		return null;
	}
}

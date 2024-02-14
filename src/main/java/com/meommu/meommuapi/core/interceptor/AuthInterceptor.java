package com.meommu.meommuapi.core.interceptor;

import static com.meommu.meommuapi.auth.exception.errorCode.AuthErrorCode.*;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.meommu.meommuapi.auth.exception.JwtException;
import com.meommu.meommuapi.auth.token.JwtTokenExtractor;
import com.meommu.meommuapi.auth.token.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	private static final String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;

	private final JwtTokenProvider jwtTokenProvider;

	public AuthInterceptor(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (CorsUtils.isPreFlightRequest(request)) {
			return true;
		}

		validateAuthorizationHeader(request);

		jwtTokenProvider.validateAccessToken(JwtTokenExtractor.extractAccessToken(request));

		return true;
	}

	private void validateAuthorizationHeader(HttpServletRequest request) {
		String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
		if (authorizationHeader == null) {
			throw new JwtException(NO_AUTHORIZATION_HEADER);
		}
		if (JwtTokenExtractor.extractAccessToken(request) == null) {
			throw new JwtException(INVALID_HEADER_FORMAT);
		}
	}
}
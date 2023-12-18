package com.meommu.meommuapi.core.auth.token;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

	private final JwtTokenProvider tokenProvider;

	public AuthenticationPrincipalArgumentResolver(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Auth.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		String token = JwtTokenExtractor.extractAccessToken(request);
		return tokenProvider.getParsedAuthInfo(token);
	}
}
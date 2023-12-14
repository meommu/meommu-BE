package com.meommu.meommuapi.common.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.meommu.meommuapi.auth.token.AuthenticationPrincipalArgumentResolver;
import com.meommu.meommuapi.auth.token.JwtTokenProvider;
import com.meommu.meommuapi.common.interceptor.AuthInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final AuthInterceptor authInterceptor;
	private final JwtTokenProvider jwtTokenProvider;

	public WebMvcConfig(AuthInterceptor authInterceptor, JwtTokenProvider jwtTokenProvider) {
		this.authInterceptor = authInterceptor;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("/html2canvas/proxy.json/**", "*")
			.allowedHeaders("*")
			.allowCredentials(false)
			.allowedMethods(
				HttpMethod.GET.name(),
				HttpMethod.HEAD.name(),
				HttpMethod.POST.name(),
				HttpMethod.PUT.name(),
				HttpMethod.PATCH.name(),
				HttpMethod.DELETE.name());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/api/v1/kindergartens/signin")
			.excludePathPatterns("/api/v1/kindergartens/signup")
			.excludePathPatterns("/api/v1/kindergartens/email/**")
			.excludePathPatterns("/api/v1/kindergartens/password")
			.excludePathPatterns("/api/v1/images/**")
			.excludePathPatterns("/api/v1/notices/**")
			.excludePathPatterns("/api/v1/diaries/shared/**")
			.excludePathPatterns("/api/v1/proxy/**")
			.excludePathPatterns("/docs/index.html");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(authenticationPrincipalArgumentResolver());
	}

	@Bean
	public AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver() {
		return new AuthenticationPrincipalArgumentResolver(jwtTokenProvider);
	}
}

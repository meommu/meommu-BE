package com.meommu.meommuapi.global.advisor;

import java.lang.reflect.Type;
import java.util.Objects;

import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.meommu.meommuapi.global.dto.ApiResponseGenerator;
import com.meommu.meommuapi.global.util.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller 응답값을 봉투패턴(Envelop pattern)으로 일정한 데이터 형식으로 가공
 * {
 *     "code": "0000",
 *     "message": "OK",
 *     "data": { data }
 * }
 */
@Slf4j
@RestControllerAdvice(basePackages = {
	"com.meommu.meommuapi.core.kindergarten.controller",
	"com.meommu.meommuapi.core.auth.controller",
	"com.meommu.meommuapi.core.image.controller",
	"com.meommu.meommuapi.core.gpt.controller",
	"com.meommu.meommuapi.core.guide.controller",
	"com.meommu.meommuapi.core.diary.controller",
	"com.meommu.meommuapi.core.notice.controller",
	"com.meommu.meommuapi.core.proxy.controller",
})
public class ApiResponseWrappingAdvisor implements ResponseBodyAdvice<Object> {

	private Type getGenericType(MethodParameter returnType) {
		if (HttpEntity.class.isAssignableFrom(returnType.getParameterType())) {
			return ResolvableType.forType(returnType.getGenericParameterType()).getGeneric().getType();
		} else {
			return returnType.getGenericParameterType();
		}
	}

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		Type type = GenericTypeResolver.resolveType(getGenericType(returnType),
			returnType.getContainingClass());

		if (Void.class.getName().equals(type.getTypeName())) {
			return false;
		}

		return !converterType.isAssignableFrom(ByteArrayHttpMessageConverter.class) &&
			!converterType.isAssignableFrom(ResourceHttpMessageConverter.class);
	}

	@Override
	public Object beforeBodyWrite(@Nullable Object body,
		@NonNull MethodParameter returnType,
		@NonNull MediaType selectedContentType,
		@NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
		@NonNull ServerHttpRequest request,
		@NonNull ServerHttpResponse response) {

		HttpStatus responseStatus = HttpStatus.valueOf(
			((ServletServerHttpResponse)response).getServletResponse().getStatus()
		);

		if (Objects.isNull(body)) {
			return responseStatus.isError() ? ApiResponseGenerator.fail() : ApiResponseGenerator.success();
		}

		var apiResponse =
			responseStatus.isError() ? ApiResponseGenerator.fail(body) : ApiResponseGenerator.success(body);
		log.trace("[ApiResponse] {}", apiResponse);

		if (selectedConverterType.isAssignableFrom(StringHttpMessageConverter.class)) {
			try {
				response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
				return JsonUtils.toJson(apiResponse);
			} catch (JsonUtils.JsonEncodeException jpe) {
				log.warn("JSON 처리 중 오류 발생", jpe);
				throw new ApiResponseJsonProcessingException(jpe);
			}
		}

		return apiResponse;
	}
}

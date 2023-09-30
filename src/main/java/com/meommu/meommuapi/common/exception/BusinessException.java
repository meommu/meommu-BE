package com.meommu.meommuapi.common.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import com.meommu.meommuapi.common.util.JsonUtils;

public abstract class BusinessException extends RuntimeException {

	protected BusinessException() {
	}

	protected BusinessException(String message) {
		super(message);
	}

	protected BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	protected BusinessException(Throwable cause) {
		super(cause);
	}

	protected BusinessException(@NonNull Map<String, Object> messageFields) {
		super(JsonUtils.toJson(messageFields));
	}

	protected BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public abstract HttpStatus getHttpStatus();

	public abstract String getErrorCode();

	public abstract boolean isNecessaryToLog();
}

package com.meommu.meommuapi.common.exception;

import org.springframework.http.HttpStatus;

public abstract class InternalServerException extends RuntimeException {

	private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

	private static final String ERROR_CODE = BusinessCode.INTERNAL_SERVER_ERROR.getCode();

	public InternalServerException(String message) {
		super(message);
	}

	public HttpStatus getHttpStatus() {
		return HTTP_STATUS;
	}

	public String getErrorCode() {
		return ERROR_CODE;
	}

	public boolean isNecessaryToLog() {
		return false;
	}
}

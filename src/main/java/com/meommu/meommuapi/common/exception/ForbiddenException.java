package com.meommu.meommuapi.common.exception;

import org.springframework.http.HttpStatus;

public abstract class ForbiddenException extends BusinessException {

	private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

	private static final String ERROR_CODE = BusinessCode.FORBIDDEN.getCode();

	protected ForbiddenException(String message) {
		super(message);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HTTP_STATUS;
	}

	@Override
	public String getErrorCode() {
		return ERROR_CODE;
	}

	@Override
	public boolean isNecessaryToLog() {
		return true;
	}
}

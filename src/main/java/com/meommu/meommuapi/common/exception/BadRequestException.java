package com.meommu.meommuapi.common.exception;

import org.springframework.http.HttpStatus;

public abstract class BadRequestException extends BusinessException {

	private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

	private static final String ERROR_CODE = BusinessCode.BAD_REQUEST.getCode();

	protected BadRequestException(String message) {
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

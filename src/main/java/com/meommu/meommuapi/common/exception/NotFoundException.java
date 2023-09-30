package com.meommu.meommuapi.common.exception;

import org.springframework.http.HttpStatus;

public abstract class NotFoundException extends BusinessException {

	private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

	private static final String ERROR_CODE = BusinessCode.NOT_FOUND.getCode();

	protected NotFoundException(String message) {
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

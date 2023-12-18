package com.meommu.meommuapi.global.exception;

import org.springframework.http.HttpStatus;

import com.meommu.meommuapi.global.exception.errorCode.BusinessErrorCode;
import com.meommu.meommuapi.global.exception.errorCode.ErrorCode;

public abstract class ForbiddenException extends BusinessException {

	private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

	private String ERROR_CODE = BusinessErrorCode.FORBIDDEN.getCode();

	protected ForbiddenException(String message) {
		super(message);
	}

	protected ForbiddenException(ErrorCode errorCode) {
		super(errorCode.getDescription());
		this.ERROR_CODE = errorCode.getCode();
	}

	protected ForbiddenException(ErrorCode errorCode, String message) {
		super(message);
		this.ERROR_CODE = errorCode.getCode();
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

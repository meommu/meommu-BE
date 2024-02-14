package com.meommu.meommuapi.core.exception;

import org.springframework.http.HttpStatus;

import com.meommu.meommuapi.core.exception.errorCode.BusinessErrorCode;
import com.meommu.meommuapi.core.exception.errorCode.ErrorCode;

public abstract class UnauthorizedException extends BusinessException {

	private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;

	private String ERROR_CODE = BusinessErrorCode.UNAUTHORIZED.getCode();

	protected UnauthorizedException(String message) {
		super(message);
	}

	protected UnauthorizedException(ErrorCode errorCode) {
		super(errorCode.getDescription());
		this.ERROR_CODE = errorCode.getCode();
	}

	protected UnauthorizedException(ErrorCode errorCode, String message) {
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

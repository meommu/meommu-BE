package com.meommu.meommuapi.global.exception;

import org.springframework.http.HttpStatus;

import com.meommu.meommuapi.global.exception.errorCode.BusinessErrorCode;
import com.meommu.meommuapi.global.exception.errorCode.ErrorCode;

public abstract class BadRequestException extends BusinessException {

	private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

	private String ERROR_CODE = BusinessErrorCode.BAD_REQUEST.getCode();

	protected BadRequestException(String message) {
		super(message);
	}

	protected BadRequestException(ErrorCode errorCode) {
		super(errorCode.getDescription());
		this.ERROR_CODE = errorCode.getCode();
	}

	protected BadRequestException(ErrorCode errorCode, String message) {
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

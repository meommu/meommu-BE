package com.meommu.meommuapi.global.exception;

import org.springframework.http.HttpStatus;

import com.meommu.meommuapi.global.exception.errorCode.BusinessErrorCode;
import com.meommu.meommuapi.global.exception.errorCode.ErrorCode;

public abstract class InternalServerException extends RuntimeException {

	private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

	private String ERROR_CODE = BusinessErrorCode.INTERNAL_SERVER_ERROR.getCode();

	public InternalServerException(String message) {
		super(message);
	}

	public InternalServerException(ErrorCode errorCode) {
		super(errorCode.getDescription());
		this.ERROR_CODE = errorCode.getCode();
	}

	public InternalServerException(ErrorCode errorCode, String message) {
		super(message);
		this.ERROR_CODE = errorCode.getCode();
	}

	public HttpStatus getHttpStatus() {
		return HTTP_STATUS;
	}

	public String getErrorCode() {
		return ERROR_CODE;
	}

	public boolean isNecessaryToLog() {
		return true;
	}
}

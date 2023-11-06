package com.meommu.meommuapi.enumTest;

import com.meommu.meommuapi.common.exception.errorCode.ErrorCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class ErrorCodeResponse {

	private String code;

	private String name;

	private String message;

	private ErrorCodeResponse() {
	}

	public ErrorCodeResponse(ErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.name = errorCode.getName();
		this.message = errorCode.getDescription();
	}
}
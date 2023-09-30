package com.meommu.meommuapi.common.dto;

import com.meommu.meommuapi.common.exception.BusinessCode;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
	private String code;
	private String message;
	private T data;

	public ApiResponse() {
	}

	public ApiResponse(BusinessCode code) {
		this.code = code.getCode();
		this.message = code.getDescription();
	}

	public ApiResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public ApiResponse(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
}


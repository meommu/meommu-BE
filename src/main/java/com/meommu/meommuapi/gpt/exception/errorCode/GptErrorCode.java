package com.meommu.meommuapi.gpt.exception.errorCode;

import com.meommu.meommuapi.core.exception.errorCode.ErrorCode;

public enum GptErrorCode implements ErrorCode {

	GPT_SERVER_EXCEPTION("G001", "GPT 서버 오류입니다.");

	private final String code;

	private final String description;

	GptErrorCode(String code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public String getName() {
		return this.name();
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
}

package com.meommu.meommuapi.core.mail.exception.errorCode;

import com.meommu.meommuapi.global.exception.errorCode.ErrorCode;

public enum MailErrorCode implements ErrorCode {

	MAIL_TRANSFER_ERROR("M001", "이메일 전송에 실패했습니다. 관리자에게 문의하세요."),
	;

	private final String code;
	private final String description;

	MailErrorCode(String code, String description) {
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

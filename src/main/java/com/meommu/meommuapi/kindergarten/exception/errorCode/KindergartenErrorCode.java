package com.meommu.meommuapi.kindergarten.exception.errorCode;

import com.meommu.meommuapi.common.exception.errorCode.ErrorCode;

public enum KindergartenErrorCode implements ErrorCode {
	DUPLICATED_EMAIL("K001", "이메일이 중복되었습니다."),
	INVALID_EMAIL_FORMAT("K002", "이메일 형식 오류입니다."),
	INVALID_NAME_FORMAT("K003", "유치원 이름 오류입니다."),
	INVALID_OWNER_NAME_FORMAT("K004", "유치원 대표자 이름 오류입니다."),
	INVALID_PASSWORD_FORMAT("K005", "비밀번호가 일치하지 않습니다."),
	INVALID_PHONE_FORMAT("K006", "휴대폰 번호 오류입니다."),
	INVALID_PASSWORD_CONFIRMATION("K007", "비밀번호가 일치하지 않습니다."),
	KINDERGARTEN_NOT_FOUND("K008", "유치원 정보를 찾을 수 없습니다.");

	private final String code;
	private final String description;

	KindergartenErrorCode(String code, String description) {
		this.code = code;
		this.description = description;
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
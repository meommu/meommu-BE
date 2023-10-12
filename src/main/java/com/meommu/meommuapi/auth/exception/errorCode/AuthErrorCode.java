package com.meommu.meommuapi.auth.exception.errorCode;

import com.meommu.meommuapi.common.exception.errorCode.ErrorCode;

public enum AuthErrorCode implements ErrorCode {
	UNSUPPORTED_JWT("A001", "지원하지 않는 토큰 형식입니다."),
	EXPIRED_JWT("A002", "토큰 기한이 만료됐습니다."),
	MALFORMED_JWT("A003", "토큰 값이 유효하지 않습니다."),
	INVALID_SIGNATURE("A004", "토큰의 서명이 잘못되었습니다."),
	NO_AUTHORIZATION_HEADER("A005", "인증 헤더를 찾을 수 없습니다."),
	INVALID_HEADER_FORMAT("A006", "인증 헤더 포멧이 올바르지 않습니다."),
	LOGIN_FAILED("A007", "아이디 혹은 비밀번호가 올바르지 않습니다."),
	NOT_AUTHORITY("A008", "해당 리소스에 접근 권한이 없습니다."),
	;

	private final String code;

	private final String description;

	AuthErrorCode(String code, String description) {
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

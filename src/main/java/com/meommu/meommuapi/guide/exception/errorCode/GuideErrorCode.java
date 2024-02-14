package com.meommu.meommuapi.guide.exception.errorCode;

import com.meommu.meommuapi.core.exception.errorCode.ErrorCode;

public enum GuideErrorCode implements ErrorCode {

	GUIDE_FORMAT_EXCEPTION("L001", "가이드 포멧 오류입니다."),
	DETAIL_FORMAT_EXCEPTION("L002", "가이드 디테일 포멧 오류입니다."),
	GUIDE_NOT_FOUND("L003", "가이드 정보를 찾을 수 없습니다."),
	DETAIL_NOT_FOUND("L004", "가이드 디테일 정보를 찾을 수 없습니다."),
	INVALID_ID("L005", "가이드 id 혹은 가이드 디테일 id가 잘못되었습니다."),
	;

	private final String code;

	private final String description;

	GuideErrorCode(String code, String description) {
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

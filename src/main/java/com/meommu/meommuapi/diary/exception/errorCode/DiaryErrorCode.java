package com.meommu.meommuapi.diary.exception.errorCode;

import com.meommu.meommuapi.common.exception.errorCode.ErrorCode;

public enum DiaryErrorCode implements ErrorCode {

	DIARY_FORMAT_EXCEPTION("D001", "일기 포멧 오류입니다."),
	DIARY_NOT_FOUND("D002", "해당하는 일기를 찾을 수 없습니다."),
	DOG_NAME_FORMAT_EXCEPTION("D003", "강아지 이름 오류입니다.");

	private final String code;

	private final String description;

	DiaryErrorCode(String code, String description) {
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

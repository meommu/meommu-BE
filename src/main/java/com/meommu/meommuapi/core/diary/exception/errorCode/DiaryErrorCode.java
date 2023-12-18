package com.meommu.meommuapi.core.diary.exception.errorCode;

import com.meommu.meommuapi.global.exception.errorCode.ErrorCode;

public enum DiaryErrorCode implements ErrorCode {

	TITLE_FORMAT_EXCEPTION("D001", "일기 제목 형식 오류입니다."),
	CONTENT_FORMAT_EXCEPTION("D002", "일기 내용 형식 오류입니다."),
	DOG_NAME_FORMAT_EXCEPTION("D003", "강아지 이름 형식 오류입니다."),
	DIARY_NOT_FOUND("D004", "해당하는 일기를 찾을 수 없습니다."),
	;

	private final String code;

	private final String description;

	DiaryErrorCode(String code, String description) {
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

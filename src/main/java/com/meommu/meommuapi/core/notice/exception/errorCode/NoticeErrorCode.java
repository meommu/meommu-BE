package com.meommu.meommuapi.core.notice.exception.errorCode;

import com.meommu.meommuapi.global.exception.errorCode.ErrorCode;

public enum NoticeErrorCode implements ErrorCode {

	NOTICE_TITLE_FORMAT_EXCEPTION("N001", "공지 제목 포멧 오류입니다."),
	NOTICE_CONTENT_FORMAT_EXCEPTION("N002", "공지 내용 포멧 오류입니다."),
	;

	private final String code;

	private final String description;

	NoticeErrorCode(String code, String description) {
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

package com.meommu.meommuapi.core.notice.exception;

import com.meommu.meommuapi.core.notice.exception.errorCode.NoticeErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class InvalidTitleFormatException extends BadRequestException {

	public InvalidTitleFormatException() {
		super(NoticeErrorCode.NOTICE_CONTENT_FORMAT_EXCEPTION);
	}
}

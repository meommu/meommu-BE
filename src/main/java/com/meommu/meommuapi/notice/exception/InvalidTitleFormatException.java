package com.meommu.meommuapi.notice.exception;

import com.meommu.meommuapi.notice.exception.errorCode.NoticeErrorCode;
import com.meommu.meommuapi.common.exception.BadRequestException;

public class InvalidTitleFormatException extends BadRequestException {

	public InvalidTitleFormatException() {
		super(NoticeErrorCode.NOTICE_CONTENT_FORMAT_EXCEPTION);
	}
}

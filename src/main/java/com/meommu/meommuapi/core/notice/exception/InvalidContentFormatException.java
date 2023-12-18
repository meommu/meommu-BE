package com.meommu.meommuapi.core.notice.exception;

import com.meommu.meommuapi.core.notice.exception.errorCode.NoticeErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class InvalidContentFormatException extends BadRequestException {

	public InvalidContentFormatException() {
		super(NoticeErrorCode.NOTICE_TITLE_FORMAT_EXCEPTION);
	}
}

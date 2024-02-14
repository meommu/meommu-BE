package com.meommu.meommuapi.notice.exception;

import com.meommu.meommuapi.notice.exception.errorCode.NoticeErrorCode;
import com.meommu.meommuapi.core.exception.BadRequestException;

public class InvalidContentFormatException extends BadRequestException {

	public InvalidContentFormatException() {
		super(NoticeErrorCode.NOTICE_TITLE_FORMAT_EXCEPTION);
	}
}

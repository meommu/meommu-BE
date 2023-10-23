package com.meommu.meommuapi.notice.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.diary.exception.errorCode.DiaryErrorCode;
import com.meommu.meommuapi.notice.exception.errorCode.NoticeErrorCode;

public class InvalidContentFormatException extends BadRequestException {

	public InvalidContentFormatException() {
		super(NoticeErrorCode.NOTICE_TITLE_FORMAT_EXCEPTION);
	}
}

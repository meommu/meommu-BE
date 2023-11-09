package com.meommu.meommuapi.diary.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.diary.exception.errorCode.DiaryErrorCode;

public class InvalidContentFormatException extends BadRequestException {

	public InvalidContentFormatException() {
		super(DiaryErrorCode.CONTENT_FORMAT_EXCEPTION);
	}
}

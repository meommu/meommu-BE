package com.meommu.meommuapi.core.diary.exception;

import com.meommu.meommuapi.core.diary.exception.errorCode.DiaryErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class InvalidContentFormatException extends BadRequestException {

	public InvalidContentFormatException() {
		super(DiaryErrorCode.CONTENT_FORMAT_EXCEPTION);
	}
}

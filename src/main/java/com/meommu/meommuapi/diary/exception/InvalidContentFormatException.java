package com.meommu.meommuapi.diary.exception;

import com.meommu.meommuapi.diary.exception.errorCode.DiaryErrorCode;
import com.meommu.meommuapi.core.exception.BadRequestException;

public class InvalidContentFormatException extends BadRequestException {

	public InvalidContentFormatException() {
		super(DiaryErrorCode.CONTENT_FORMAT_EXCEPTION);
	}
}

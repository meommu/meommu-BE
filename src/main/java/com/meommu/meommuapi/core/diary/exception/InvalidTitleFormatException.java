package com.meommu.meommuapi.core.diary.exception;

import com.meommu.meommuapi.core.diary.exception.errorCode.DiaryErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class InvalidTitleFormatException extends BadRequestException {

	public InvalidTitleFormatException() {
		super(DiaryErrorCode.TITLE_FORMAT_EXCEPTION);
	}
}

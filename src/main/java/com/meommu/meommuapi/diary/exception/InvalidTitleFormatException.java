package com.meommu.meommuapi.diary.exception;

import com.meommu.meommuapi.diary.exception.errorCode.DiaryErrorCode;
import com.meommu.meommuapi.common.exception.BadRequestException;

public class InvalidTitleFormatException extends BadRequestException {

	public InvalidTitleFormatException() {
		super(DiaryErrorCode.TITLE_FORMAT_EXCEPTION);
	}
}

package com.meommu.meommuapi.diary.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.diary.exception.errorCode.DiaryErrorCode;

public class InvalidTitleFormatException extends BadRequestException {

	public InvalidTitleFormatException() {
		super(DiaryErrorCode.DIARY_FORMAT_EXCEPTION);
	}
}

package com.meommu.meommuapi.core.diary.exception;

import com.meommu.meommuapi.core.diary.exception.errorCode.DiaryErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class InvalidDogNameFormatException extends BadRequestException {

	public InvalidDogNameFormatException() {
		super(DiaryErrorCode.DOG_NAME_FORMAT_EXCEPTION);
	}
}

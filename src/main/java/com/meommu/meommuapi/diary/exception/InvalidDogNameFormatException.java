package com.meommu.meommuapi.diary.exception;

import com.meommu.meommuapi.diary.exception.errorCode.DiaryErrorCode;
import com.meommu.meommuapi.core.exception.BadRequestException;

public class InvalidDogNameFormatException extends BadRequestException {

	public InvalidDogNameFormatException() {
		super(DiaryErrorCode.DOG_NAME_FORMAT_EXCEPTION);
	}
}

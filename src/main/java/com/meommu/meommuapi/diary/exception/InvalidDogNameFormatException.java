package com.meommu.meommuapi.diary.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.diary.exception.errorCode.DiaryErrorCode;

public class InvalidDogNameFormatException extends BadRequestException {

	public InvalidDogNameFormatException() {
		super(DiaryErrorCode.DOG_NAME_FORMAT_EXCEPTION);
	}
}

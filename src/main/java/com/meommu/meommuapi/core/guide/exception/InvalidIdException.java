package com.meommu.meommuapi.core.guide.exception;

import com.meommu.meommuapi.core.guide.exception.errorCode.GuideErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class InvalidIdException extends BadRequestException {

	public InvalidIdException() {
		super(GuideErrorCode.INVALID_ID);
	}
}

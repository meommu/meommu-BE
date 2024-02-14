package com.meommu.meommuapi.guide.exception;

import com.meommu.meommuapi.guide.exception.errorCode.GuideErrorCode;
import com.meommu.meommuapi.core.exception.BadRequestException;

public class InvalidIdException extends BadRequestException {

	public InvalidIdException() {
		super(GuideErrorCode.INVALID_ID);
	}
}

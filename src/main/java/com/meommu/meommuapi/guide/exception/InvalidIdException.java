package com.meommu.meommuapi.guide.exception;

import com.meommu.meommuapi.guide.exception.errorCode.GuideErrorCode;
import com.meommu.meommuapi.common.exception.BadRequestException;

public class InvalidIdException extends BadRequestException {

	public InvalidIdException() {
		super(GuideErrorCode.INVALID_ID);
	}
}

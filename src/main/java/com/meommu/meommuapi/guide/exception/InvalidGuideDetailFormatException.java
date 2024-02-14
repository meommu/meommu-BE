package com.meommu.meommuapi.guide.exception;

import com.meommu.meommuapi.guide.exception.errorCode.GuideErrorCode;
import com.meommu.meommuapi.core.exception.BadRequestException;

public class InvalidGuideDetailFormatException extends BadRequestException {

	public InvalidGuideDetailFormatException() {
		super(GuideErrorCode.DETAIL_FORMAT_EXCEPTION);
	}
}

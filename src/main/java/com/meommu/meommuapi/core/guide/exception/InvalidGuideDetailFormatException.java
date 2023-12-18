package com.meommu.meommuapi.core.guide.exception;

import com.meommu.meommuapi.core.guide.exception.errorCode.GuideErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class InvalidGuideDetailFormatException extends BadRequestException {

	public InvalidGuideDetailFormatException() {
		super(GuideErrorCode.DETAIL_FORMAT_EXCEPTION);
	}
}

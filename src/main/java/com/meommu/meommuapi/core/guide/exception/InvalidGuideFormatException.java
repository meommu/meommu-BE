package com.meommu.meommuapi.core.guide.exception;

import com.meommu.meommuapi.core.guide.exception.errorCode.GuideErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class InvalidGuideFormatException extends BadRequestException {

	public InvalidGuideFormatException() {
		super(GuideErrorCode.GUIDE_FORMAT_EXCEPTION);
	}
}

package com.meommu.meommuapi.guide.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.guide.exception.errorCode.GuideErrorCode;

public class InvalidGuideFormatException extends BadRequestException {

	public InvalidGuideFormatException() {
		super(GuideErrorCode.GUIDE_FORMAT_EXCEPTION);
	}
}

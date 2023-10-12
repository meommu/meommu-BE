package com.meommu.meommuapi.guide.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.guide.exception.errorCode.GuideErrorCode;

public class InvalidGuideDetailFormatException extends BadRequestException {

	public InvalidGuideDetailFormatException() {
		super(GuideErrorCode.DETAIL_FORMAT_EXCEPTION);
	}
}

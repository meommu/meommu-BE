package com.meommu.meommuapi.core.kindergarten.exception;

import com.meommu.meommuapi.core.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class InvalidNameFormatException extends BadRequestException {

	public InvalidNameFormatException() {
		super(KindergartenErrorCode.INVALID_NAME_FORMAT);
	}
}

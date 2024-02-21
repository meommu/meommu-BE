package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.common.exception.BadRequestException;

public class InvalidNameFormatException extends BadRequestException {

	public InvalidNameFormatException() {
		super(KindergartenErrorCode.INVALID_NAME_FORMAT);
	}
}

package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;

public class InvalidNameFormatException extends BadRequestException {

	public InvalidNameFormatException() {
		super(KindergartenErrorCode.INVALID_NAME_FORMAT);
	}
}

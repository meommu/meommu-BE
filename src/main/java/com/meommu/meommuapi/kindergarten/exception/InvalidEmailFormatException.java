package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.core.exception.BadRequestException;

public class InvalidEmailFormatException extends BadRequestException {

	public InvalidEmailFormatException() {
		super(KindergartenErrorCode.INVALID_EMAIL_FORMAT);
	}
}

package com.meommu.meommuapi.core.kindergarten.exception;

import com.meommu.meommuapi.core.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class InvalidEmailFormatException extends BadRequestException {

	public InvalidEmailFormatException() {
		super(KindergartenErrorCode.INVALID_EMAIL_FORMAT);
	}
}

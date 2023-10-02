package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;

public class InvalidEmailFormatException extends BadRequestException {

	public InvalidEmailFormatException() {
		super(KindergartenErrorCode.INVALID_EMAIL_FORMAT);
	}
}

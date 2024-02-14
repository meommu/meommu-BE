package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.core.exception.BadRequestException;

public class InvalidPasswordFormatException extends BadRequestException {

	public InvalidPasswordFormatException() {
		super(KindergartenErrorCode.INVALID_PASSWORD_FORMAT);
	}
}

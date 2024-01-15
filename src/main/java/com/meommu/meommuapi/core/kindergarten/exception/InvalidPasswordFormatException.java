package com.meommu.meommuapi.core.kindergarten.exception;

import com.meommu.meommuapi.core.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class InvalidPasswordFormatException extends BadRequestException {

	public InvalidPasswordFormatException() {
		super(KindergartenErrorCode.INVALID_PASSWORD_FORMAT);
	}
}

package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;

public class InvalidPasswordFormatException extends BadRequestException {

	public InvalidPasswordFormatException() {
		super(KindergartenErrorCode.INVALID_PASSWORD_FORMAT);
	}
}

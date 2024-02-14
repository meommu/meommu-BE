package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.core.exception.BadRequestException;

public class InvalidPhoneFormatException extends BadRequestException {

	public InvalidPhoneFormatException() {
		super(KindergartenErrorCode.INVALID_PHONE_FORMAT);
	}
}

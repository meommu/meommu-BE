package com.meommu.meommuapi.core.kindergarten.exception;

import com.meommu.meommuapi.core.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class InvalidPhoneFormatException extends BadRequestException {

	public InvalidPhoneFormatException() {
		super(KindergartenErrorCode.INVALID_PHONE_FORMAT);
	}
}

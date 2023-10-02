package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;

public class InvalidPhoneFormatException extends BadRequestException {

	public InvalidPhoneFormatException() {
		super(KindergartenErrorCode.INVALID_PHONE_FORMAT);
	}
}

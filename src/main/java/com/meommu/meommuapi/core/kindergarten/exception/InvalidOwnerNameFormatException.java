package com.meommu.meommuapi.core.kindergarten.exception;

import com.meommu.meommuapi.global.exception.BadRequestException;
import com.meommu.meommuapi.core.kindergarten.exception.errorCode.KindergartenErrorCode;

public class InvalidOwnerNameFormatException extends BadRequestException {

	public InvalidOwnerNameFormatException() {
		super(KindergartenErrorCode.INVALID_OWNER_NAME_FORMAT);
	}
}

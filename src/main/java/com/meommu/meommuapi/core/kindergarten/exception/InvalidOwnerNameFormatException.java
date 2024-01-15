package com.meommu.meommuapi.core.kindergarten.exception;

import com.meommu.meommuapi.core.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class InvalidOwnerNameFormatException extends BadRequestException {

	public InvalidOwnerNameFormatException() {
		super(KindergartenErrorCode.INVALID_OWNER_NAME_FORMAT);
	}
}

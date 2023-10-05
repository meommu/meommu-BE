package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;

public class InvalidOwnerNameFormatException extends BadRequestException {

	public InvalidOwnerNameFormatException() {
		super(KindergartenErrorCode.INVALID_OWNER_NAME_FORMAT);
	}
}

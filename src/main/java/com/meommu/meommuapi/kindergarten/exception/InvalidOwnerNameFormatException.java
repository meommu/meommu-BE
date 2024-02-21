package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.common.exception.BadRequestException;

public class InvalidOwnerNameFormatException extends BadRequestException {

	public InvalidOwnerNameFormatException() {
		super(KindergartenErrorCode.INVALID_OWNER_NAME_FORMAT);
	}
}

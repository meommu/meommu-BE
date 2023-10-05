package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;

public class InvalidPasswordConfirmationException extends BadRequestException {

	public InvalidPasswordConfirmationException() {
		super(KindergartenErrorCode.INVALID_PASSWORD_CONFIRMATION);
	}
}

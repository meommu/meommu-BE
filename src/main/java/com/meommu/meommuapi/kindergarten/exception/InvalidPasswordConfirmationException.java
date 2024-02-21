package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.common.exception.BadRequestException;

public class InvalidPasswordConfirmationException extends BadRequestException {

	public InvalidPasswordConfirmationException() {
		super(KindergartenErrorCode.INVALID_PASSWORD_CONFIRMATION);
	}
}

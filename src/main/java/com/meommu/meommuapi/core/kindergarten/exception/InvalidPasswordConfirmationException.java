package com.meommu.meommuapi.core.kindergarten.exception;

import com.meommu.meommuapi.global.exception.BadRequestException;
import com.meommu.meommuapi.core.kindergarten.exception.errorCode.KindergartenErrorCode;

public class InvalidPasswordConfirmationException extends BadRequestException {

	public InvalidPasswordConfirmationException() {
		super(KindergartenErrorCode.INVALID_PASSWORD_CONFIRMATION);
	}
}

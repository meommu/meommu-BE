package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.core.exception.BadRequestException;

public class EmailCodeInvalidException extends BadRequestException {

	public EmailCodeInvalidException() {
		super(KindergartenErrorCode.AUTH_CODE_INVALID);
	}

}

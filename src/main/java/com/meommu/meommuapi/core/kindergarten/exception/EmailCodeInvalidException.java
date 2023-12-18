package com.meommu.meommuapi.core.kindergarten.exception;

import com.meommu.meommuapi.global.exception.BadRequestException;
import com.meommu.meommuapi.core.kindergarten.exception.errorCode.KindergartenErrorCode;

public class EmailCodeInvalidException extends BadRequestException {

	public EmailCodeInvalidException() {
		super(KindergartenErrorCode.AUTH_CODE_INVALID);
	}

}

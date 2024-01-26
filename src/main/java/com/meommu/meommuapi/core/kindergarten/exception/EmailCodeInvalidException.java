package com.meommu.meommuapi.core.kindergarten.exception;

import com.meommu.meommuapi.core.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class EmailCodeInvalidException extends BadRequestException {

	public EmailCodeInvalidException() {
		super(KindergartenErrorCode.AUTH_CODE_INVALID);
	}

}

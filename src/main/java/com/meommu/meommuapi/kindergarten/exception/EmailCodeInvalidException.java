package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;

public class EmailCodeInvalidException extends BadRequestException {

	public EmailCodeInvalidException() {
		super(KindergartenErrorCode.AUTH_CODE_INVALID);
	}

}

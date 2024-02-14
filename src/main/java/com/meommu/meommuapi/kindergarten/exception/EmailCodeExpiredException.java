package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.core.exception.BadRequestException;

public class EmailCodeExpiredException extends BadRequestException {

	public EmailCodeExpiredException() {
		super(KindergartenErrorCode.AUTH_CODE_EXPIRED);
	}

}

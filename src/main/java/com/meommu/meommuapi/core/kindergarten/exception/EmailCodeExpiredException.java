package com.meommu.meommuapi.core.kindergarten.exception;

import com.meommu.meommuapi.core.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class EmailCodeExpiredException extends BadRequestException {

	public EmailCodeExpiredException() {
		super(KindergartenErrorCode.AUTH_CODE_EXPIRED);
	}

}

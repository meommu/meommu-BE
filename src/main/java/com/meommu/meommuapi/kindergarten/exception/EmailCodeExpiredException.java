package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.common.exception.NotFoundException;
import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;

public class EmailCodeExpiredException extends BadRequestException {

	public EmailCodeExpiredException() {
		super(KindergartenErrorCode.AUTH_CODE_EXPIRED);
	}

}

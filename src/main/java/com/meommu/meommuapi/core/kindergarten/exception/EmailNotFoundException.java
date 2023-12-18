package com.meommu.meommuapi.core.kindergarten.exception;

import com.meommu.meommuapi.global.exception.NotFoundException;
import com.meommu.meommuapi.core.kindergarten.exception.errorCode.KindergartenErrorCode;

public class EmailNotFoundException extends NotFoundException {

	public EmailNotFoundException() {
		super(KindergartenErrorCode.EMAIL_KINDERGARTEN_NOT_FOUND);
	}

}

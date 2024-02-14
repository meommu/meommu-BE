package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.core.exception.NotFoundException;

public class EmailNotFoundException extends NotFoundException {

	public EmailNotFoundException() {
		super(KindergartenErrorCode.EMAIL_KINDERGARTEN_NOT_FOUND);
	}

}

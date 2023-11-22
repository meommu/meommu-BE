package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.common.exception.NotFoundException;
import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;

public class EmailNotFoundException extends NotFoundException {

	public EmailNotFoundException() {
		super(KindergartenErrorCode.EMAIL_KINDERGARTEN_NOT_FOUND);
	}

}

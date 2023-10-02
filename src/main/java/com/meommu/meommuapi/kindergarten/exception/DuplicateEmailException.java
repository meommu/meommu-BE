package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;

public class DuplicateEmailException extends BadRequestException {

	public DuplicateEmailException() {
		super(KindergartenErrorCode.DUPLICATED_EMAIL);
	}
}

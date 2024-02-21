package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.common.exception.BadRequestException;

public class DuplicateEmailException extends BadRequestException {

	public DuplicateEmailException() {
		super(KindergartenErrorCode.DUPLICATED_EMAIL);
	}
}

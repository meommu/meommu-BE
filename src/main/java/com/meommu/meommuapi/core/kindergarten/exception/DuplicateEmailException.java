package com.meommu.meommuapi.core.kindergarten.exception;

import com.meommu.meommuapi.core.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class DuplicateEmailException extends BadRequestException {

	public DuplicateEmailException() {
		super(KindergartenErrorCode.DUPLICATED_EMAIL);
	}
}

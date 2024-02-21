package com.meommu.meommuapi.kindergarten.exception;

import com.meommu.meommuapi.kindergarten.exception.errorCode.KindergartenErrorCode;
import com.meommu.meommuapi.common.exception.NotFoundException;

public class KindergartenNotFoundException extends NotFoundException {

	private static final String MESSAGE = "유치원(id = %d)를 찾을 수 없습니다.";

	public KindergartenNotFoundException() {
		super(KindergartenErrorCode.KINDERGARTEN_NOT_FOUND);
	}

	public KindergartenNotFoundException(long id) {
		super(KindergartenErrorCode.KINDERGARTEN_NOT_FOUND, String.format(MESSAGE, id));
	}
}

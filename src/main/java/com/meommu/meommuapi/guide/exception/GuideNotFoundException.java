package com.meommu.meommuapi.guide.exception;

import com.meommu.meommuapi.common.exception.NotFoundException;
import com.meommu.meommuapi.guide.exception.errorCode.GuideErrorCode;

public class GuideNotFoundException extends NotFoundException {

	private static final String MESSAGE = "가이드(id = %d)를 찾을 수 없습니다.";

	public GuideNotFoundException() {
		super(GuideErrorCode.GUIDE_NOT_FOUND);
	}

	public GuideNotFoundException(long id) {
		super(GuideErrorCode.GUIDE_NOT_FOUND, String.format(MESSAGE, id));
	}
}

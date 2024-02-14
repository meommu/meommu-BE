package com.meommu.meommuapi.guide.exception;

import com.meommu.meommuapi.guide.exception.errorCode.GuideErrorCode;
import com.meommu.meommuapi.core.exception.NotFoundException;

public class GuideDetailNotFoundException extends NotFoundException {

	private static final String MESSAGE = "가이드 디테일(id = %d)을 찾을 수 없습니다.";

	public GuideDetailNotFoundException() {
		super(GuideErrorCode.GUIDE_NOT_FOUND);
	}

	public GuideDetailNotFoundException(long id) {
		super(GuideErrorCode.GUIDE_NOT_FOUND, String.format(MESSAGE, id));
	}
}

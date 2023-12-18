package com.meommu.meommuapi.core.diary.exception;

import com.meommu.meommuapi.core.diary.exception.errorCode.DiaryErrorCode;
import com.meommu.meommuapi.global.exception.NotFoundException;

public class DiaryNotFoundException extends NotFoundException {

	private static final String MESSAGE = "일기(id = %d)를 찾을 수 없습니다.";

	public DiaryNotFoundException() {
		super(DiaryErrorCode.DIARY_NOT_FOUND);
	}

	public DiaryNotFoundException(long id) {
		super(DiaryErrorCode.DIARY_NOT_FOUND, String.format(MESSAGE, id));
	}
}

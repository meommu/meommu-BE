package com.meommu.meommuapi.core.image.exception;

import com.meommu.meommuapi.global.exception.NotFoundException;
import com.meommu.meommuapi.core.image.exception.errorCode.ImageErrorCode;

public class ImageNotFoundException extends NotFoundException {

	private static final String MESSAGE = "이미지(id = %d)를 찾을 수 없습니다.";

	public ImageNotFoundException() {
		super(ImageErrorCode.IMAGE_NOT_FOUND);
	}

	public ImageNotFoundException(long id) {
		super(ImageErrorCode.IMAGE_NOT_FOUND, String.format(MESSAGE, id));
	}
}

package com.meommu.meommuapi.core.image.exception;

import com.meommu.meommuapi.core.image.exception.errorCode.ImageErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class InvalidImageCountException extends BadRequestException {

	public InvalidImageCountException() {
		super(ImageErrorCode.IMAGE_MAX_SIZE_ERROR);
	}

}

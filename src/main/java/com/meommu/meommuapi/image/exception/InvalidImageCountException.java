package com.meommu.meommuapi.image.exception;

import com.meommu.meommuapi.image.exception.errorCode.ImageErrorCode;
import com.meommu.meommuapi.core.exception.BadRequestException;

public class InvalidImageCountException extends BadRequestException {

	public InvalidImageCountException() {
		super(ImageErrorCode.IMAGE_MAX_SIZE_ERROR);
	}

}

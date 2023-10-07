package com.meommu.meommuapi.image.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.image.exception.errorCode.ImageErrorCode;

public class InvalidImageCountException extends BadRequestException {

	public InvalidImageCountException() {
		super(ImageErrorCode.IMAGE_MAX_SIZE_ERROR);
	}

}

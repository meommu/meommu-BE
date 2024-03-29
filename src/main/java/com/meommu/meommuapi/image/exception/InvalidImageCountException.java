package com.meommu.meommuapi.image.exception;

import com.meommu.meommuapi.image.exception.errorCode.ImageErrorCode;
import com.meommu.meommuapi.common.exception.BadRequestException;

public class InvalidImageCountException extends BadRequestException {

	public InvalidImageCountException() {
		super(ImageErrorCode.IMAGE_MAX_SIZE_ERROR);
	}

}

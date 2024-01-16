package com.meommu.meommuapi.core.image.exception;

import com.meommu.meommuapi.core.image.exception.errorCode.ImageErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class InvalidImageTypeExeption extends BadRequestException {

	public InvalidImageTypeExeption() {
		super(ImageErrorCode.INVALID_IMAGE_TYPE);
	}

}

package com.meommu.meommuapi.image.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.image.exception.errorCode.ImageErrorCode;

public class InvalidImageTypeExeption extends BadRequestException {

	public InvalidImageTypeExeption() {
		super(ImageErrorCode.INVALID_IMAGE_TYPE);
	}

}

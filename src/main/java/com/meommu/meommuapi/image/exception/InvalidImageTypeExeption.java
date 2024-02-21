package com.meommu.meommuapi.image.exception;

import com.meommu.meommuapi.image.exception.errorCode.ImageErrorCode;
import com.meommu.meommuapi.common.exception.BadRequestException;

public class InvalidImageTypeExeption extends BadRequestException {

	public InvalidImageTypeExeption() {
		super(ImageErrorCode.INVALID_IMAGE_TYPE);
	}

}

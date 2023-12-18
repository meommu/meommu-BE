package com.meommu.meommuapi.core.image.exception;

import com.meommu.meommuapi.core.image.exception.errorCode.ImageErrorCode;
import com.meommu.meommuapi.global.exception.BadRequestException;

public class ImageArgumentException extends BadRequestException {


	public ImageArgumentException() {
		super(ImageErrorCode.IMAGE_ARGUMENT_ERROR);
	}
}

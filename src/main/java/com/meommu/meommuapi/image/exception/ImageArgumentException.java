package com.meommu.meommuapi.image.exception;

import com.meommu.meommuapi.image.exception.errorCode.ImageErrorCode;
import com.meommu.meommuapi.core.exception.BadRequestException;

public class ImageArgumentException extends BadRequestException {


	public ImageArgumentException() {
		super(ImageErrorCode.IMAGE_ARGUMENT_ERROR);
	}
}

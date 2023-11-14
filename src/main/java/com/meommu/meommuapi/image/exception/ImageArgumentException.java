package com.meommu.meommuapi.image.exception;

import com.meommu.meommuapi.common.exception.BadRequestException;
import com.meommu.meommuapi.image.exception.errorCode.ImageErrorCode;

public class ImageArgumentException extends BadRequestException {


	public ImageArgumentException() {
		super(ImageErrorCode.IMAGE_ARGUMENT_ERROR);
	}
}

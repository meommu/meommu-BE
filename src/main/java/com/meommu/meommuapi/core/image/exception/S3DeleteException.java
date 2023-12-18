package com.meommu.meommuapi.core.image.exception;

import com.meommu.meommuapi.global.exception.InternalServerException;
import com.meommu.meommuapi.core.image.exception.errorCode.ImageErrorCode;

public class S3DeleteException extends InternalServerException {
	public S3DeleteException() {
		super(ImageErrorCode.IMAGE_DELETE_FAIL);
	}
}

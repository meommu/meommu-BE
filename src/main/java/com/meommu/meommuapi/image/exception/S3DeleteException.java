package com.meommu.meommuapi.image.exception;

import com.meommu.meommuapi.image.exception.errorCode.ImageErrorCode;
import com.meommu.meommuapi.core.exception.InternalServerException;

public class S3DeleteException extends InternalServerException {
	public S3DeleteException() {
		super(ImageErrorCode.IMAGE_DELETE_FAIL);
	}
}

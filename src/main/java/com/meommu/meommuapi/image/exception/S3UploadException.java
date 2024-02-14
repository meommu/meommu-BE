package com.meommu.meommuapi.image.exception;

import com.meommu.meommuapi.image.exception.errorCode.ImageErrorCode;
import com.meommu.meommuapi.core.exception.InternalServerException;

public class S3UploadException extends InternalServerException {
	public S3UploadException() {
		super(ImageErrorCode.IMAGE_UPLOAD_FAIL);
	}
}

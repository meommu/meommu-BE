package com.meommu.meommuapi.core.image.exception;

import com.meommu.meommuapi.core.image.exception.errorCode.ImageErrorCode;
import com.meommu.meommuapi.global.exception.InternalServerException;

public class S3UploadException extends InternalServerException {
	public S3UploadException() {
		super(ImageErrorCode.IMAGE_UPLOAD_FAIL);
	}
}

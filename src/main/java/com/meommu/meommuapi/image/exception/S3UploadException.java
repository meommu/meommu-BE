package com.meommu.meommuapi.image.exception;

import com.meommu.meommuapi.common.exception.InternalServerException;
import com.meommu.meommuapi.image.exception.errorCode.ImageErrorCode;

public class S3UploadException extends InternalServerException {
	public S3UploadException() {
		super(ImageErrorCode.IMAGE_UPLOAD_FAIL);
	}
}

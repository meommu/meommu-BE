package com.meommu.meommuapi.image.exception;

import com.meommu.meommuapi.image.exception.errorCode.ImageErrorCode;
import com.meommu.meommuapi.common.exception.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {

	public CategoryNotFoundException() {
		super(ImageErrorCode.CATEGORY_NOT_FOUND);
	}

}

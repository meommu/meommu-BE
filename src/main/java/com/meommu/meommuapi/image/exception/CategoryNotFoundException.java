package com.meommu.meommuapi.image.exception;

import com.meommu.meommuapi.common.exception.NotFoundException;
import com.meommu.meommuapi.image.exception.errorCode.ImageErrorCode;

public class CategoryNotFoundException extends NotFoundException {

	public CategoryNotFoundException() {
		super(ImageErrorCode.CATEGORY_NOT_FOUND);
	}

}

package com.meommu.meommuapi.core.image.exception;

import com.meommu.meommuapi.core.image.exception.errorCode.ImageErrorCode;
import com.meommu.meommuapi.global.exception.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {

	public CategoryNotFoundException() {
		super(ImageErrorCode.CATEGORY_NOT_FOUND);
	}

}

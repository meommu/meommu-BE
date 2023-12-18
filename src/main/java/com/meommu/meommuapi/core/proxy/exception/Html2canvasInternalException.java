package com.meommu.meommuapi.core.proxy.exception;

import com.meommu.meommuapi.core.proxy.exception.errorCode.ProxyErrorCode;
import com.meommu.meommuapi.global.exception.InternalServerException;

public class Html2canvasInternalException extends InternalServerException {

	public Html2canvasInternalException() {
		super(ProxyErrorCode.HTML_2_CANVAS_ERROR);
	}

}

package com.meommu.meommuapi.proxy.exception;

import com.meommu.meommuapi.common.exception.InternalServerException;
import com.meommu.meommuapi.proxy.exception.errorCode.ProxyErrorCode;

public class Html2canvasInternalException extends InternalServerException {

	public Html2canvasInternalException() {
		super(ProxyErrorCode.HTML_2_CANVAS_ERROR);
	}

}

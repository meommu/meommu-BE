package com.meommu.meommuapi.gpt.exception;

import com.meommu.meommuapi.gpt.exception.errorCode.GptErrorCode;
import com.meommu.meommuapi.common.exception.InternalServerException;

public class GptGenerateException extends InternalServerException {

	public GptGenerateException() {
		super(GptErrorCode.GPT_SERVER_EXCEPTION);
	}
}

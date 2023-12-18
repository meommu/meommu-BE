package com.meommu.meommuapi.core.gpt.exception;

import com.meommu.meommuapi.core.gpt.exception.errorCode.GptErrorCode;
import com.meommu.meommuapi.global.exception.InternalServerException;

public class GptGenerateException extends InternalServerException {

	public GptGenerateException() {
		super(GptErrorCode.GPT_SERVER_EXCEPTION);
	}
}

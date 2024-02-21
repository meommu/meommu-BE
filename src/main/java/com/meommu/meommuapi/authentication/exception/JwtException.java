package com.meommu.meommuapi.authentication.exception;

import com.meommu.meommuapi.common.exception.UnauthorizedException;
import com.meommu.meommuapi.common.exception.errorCode.ErrorCode;

public class JwtException extends UnauthorizedException {
	public JwtException(ErrorCode errorCode) {
		super(errorCode);
	}
}

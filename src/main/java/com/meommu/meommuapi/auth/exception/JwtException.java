package com.meommu.meommuapi.auth.exception;

import com.meommu.meommuapi.core.exception.UnauthorizedException;
import com.meommu.meommuapi.core.exception.errorCode.ErrorCode;

public class JwtException extends UnauthorizedException {
	public JwtException(ErrorCode errorCode) {
		super(errorCode);
	}
}

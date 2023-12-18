package com.meommu.meommuapi.core.auth.exception;

import com.meommu.meommuapi.global.exception.UnauthorizedException;
import com.meommu.meommuapi.global.exception.errorCode.ErrorCode;

public class JwtException extends UnauthorizedException {
	public JwtException(ErrorCode errorCode) {
		super(errorCode);
	}
}

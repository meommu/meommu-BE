package com.meommu.meommuapi.core.auth.exception;

import com.meommu.meommuapi.core.auth.exception.errorCode.AuthErrorCode;
import com.meommu.meommuapi.global.exception.ForbiddenException;

public class AuthorizationException extends ForbiddenException {

	public AuthorizationException() {
		super(AuthErrorCode.NOT_AUTHORITY);
	}
}

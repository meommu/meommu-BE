package com.meommu.meommuapi.auth.exception;

import com.meommu.meommuapi.auth.exception.errorCode.AuthErrorCode;
import com.meommu.meommuapi.core.exception.ForbiddenException;

public class AuthorizationException extends ForbiddenException {

	public AuthorizationException() {
		super(AuthErrorCode.NOT_AUTHORITY);
	}
}

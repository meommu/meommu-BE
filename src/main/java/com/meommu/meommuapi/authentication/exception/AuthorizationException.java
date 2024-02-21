package com.meommu.meommuapi.authentication.exception;

import com.meommu.meommuapi.authentication.exception.errorCode.AuthErrorCode;
import com.meommu.meommuapi.common.exception.ForbiddenException;

public class AuthorizationException extends ForbiddenException {

	public AuthorizationException() {
		super(AuthErrorCode.NOT_AUTHORITY);
	}
}

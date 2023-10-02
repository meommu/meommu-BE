package com.meommu.meommuapi.auth.exception;

import com.meommu.meommuapi.auth.exception.errorCode.AuthErrorCode;
import com.meommu.meommuapi.common.exception.UnauthorizedException;

public class LoginFailedException extends UnauthorizedException {
	public LoginFailedException() {
		super(AuthErrorCode.LOGIN_FAILED);
	}
}

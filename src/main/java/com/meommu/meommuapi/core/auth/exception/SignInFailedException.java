package com.meommu.meommuapi.core.auth.exception;

import com.meommu.meommuapi.core.auth.exception.errorCode.AuthErrorCode;
import com.meommu.meommuapi.global.exception.UnauthorizedException;

public class SignInFailedException extends UnauthorizedException {
	public SignInFailedException() {
		super(AuthErrorCode.SIGN_IN_FAILED);
	}
}

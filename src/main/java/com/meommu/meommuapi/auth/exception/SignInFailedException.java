package com.meommu.meommuapi.auth.exception;

import com.meommu.meommuapi.auth.exception.errorCode.AuthErrorCode;
import com.meommu.meommuapi.core.exception.UnauthorizedException;

public class SignInFailedException extends UnauthorizedException {
	public SignInFailedException() {
		super(AuthErrorCode.SIGN_IN_FAILED);
	}
}

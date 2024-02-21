package com.meommu.meommuapi.authentication.exception;

import com.meommu.meommuapi.authentication.exception.errorCode.AuthErrorCode;
import com.meommu.meommuapi.common.exception.UnauthorizedException;

public class SignInFailedException extends UnauthorizedException {
	public SignInFailedException() {
		super(AuthErrorCode.SIGN_IN_FAILED);
	}
}

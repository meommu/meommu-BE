package com.meommu.meommuapi.authentication.application;

import com.meommu.meommuapi.authentication.dto.AuthInfo;
import com.meommu.meommuapi.authentication.dto.SignInRequest;
import com.meommu.meommuapi.authentication.dto.ReissueRequest;
import com.meommu.meommuapi.authentication.dto.TokenResponse;

public interface AuthService {

	TokenResponse signIn(SignInRequest request);

	TokenResponse reissue(AuthInfo authInfo, ReissueRequest request);

	void signOut(AuthInfo authInfo);
}

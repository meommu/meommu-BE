package com.meommu.meommuapi.auth.application;

import com.meommu.meommuapi.auth.application.dto.AuthInfo;
import com.meommu.meommuapi.auth.application.dto.SignInRequest;
import com.meommu.meommuapi.auth.application.dto.ReissueRequest;
import com.meommu.meommuapi.auth.application.dto.TokenResponse;

public interface AuthService {

	TokenResponse signIn(SignInRequest request);

	TokenResponse reissue(AuthInfo authInfo, ReissueRequest request);

	void signOut(AuthInfo authInfo);
}

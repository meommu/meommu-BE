package com.meommu.meommuapi.core.auth.service;

import com.meommu.meommuapi.core.auth.dto.AuthInfo;
import com.meommu.meommuapi.core.auth.dto.ReissueRequest;
import com.meommu.meommuapi.core.auth.dto.SignInRequest;
import com.meommu.meommuapi.core.auth.dto.TokenResponse;

public interface AuthService {

	TokenResponse signIn(SignInRequest request);

	TokenResponse reissue(AuthInfo authInfo, ReissueRequest request);

	void signOut(AuthInfo authInfo);
}

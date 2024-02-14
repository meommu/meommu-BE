package com.meommu.meommuapi.auth.service;

import com.meommu.meommuapi.auth.dto.AuthInfo;
import com.meommu.meommuapi.auth.dto.SignInRequest;
import com.meommu.meommuapi.auth.dto.ReissueRequest;
import com.meommu.meommuapi.auth.dto.TokenResponse;

public interface AuthService {

	TokenResponse signIn(SignInRequest request);

	TokenResponse reissue(AuthInfo authInfo, ReissueRequest request);

	void signOut(AuthInfo authInfo);
}

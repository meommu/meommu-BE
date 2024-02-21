package com.meommu.meommuapi.kindergarten.application;

import com.meommu.meommuapi.auth.application.dto.AuthInfo;
import com.meommu.meommuapi.kindergarten.application.dto.EmailRequest;
import com.meommu.meommuapi.kindergarten.application.dto.KindergartenPasswordUpdateRequest;
import com.meommu.meommuapi.kindergarten.application.dto.KindergartenResponse;
import com.meommu.meommuapi.kindergarten.application.dto.KindergartenUpdateRequest;
import com.meommu.meommuapi.kindergarten.application.dto.MyInfoResponse;
import com.meommu.meommuapi.kindergarten.application.dto.SignUpRequest;

public interface KindergartenService {

	MyInfoResponse signUp(SignUpRequest signUpRequest);

	boolean existsByEmail(EmailRequest emailRequest);

	MyInfoResponse findMyInfo(AuthInfo authInfo);

	KindergartenResponse find(AuthInfo authInfo);

	void update(KindergartenUpdateRequest kindergartenUpdateRequest, AuthInfo authInfo);

	void delete(AuthInfo authInfo);

	void sendCodeToEmail(String email);

	boolean verifiedCode(String email, String code);

	void updatePassword(String email, KindergartenPasswordUpdateRequest request);

}

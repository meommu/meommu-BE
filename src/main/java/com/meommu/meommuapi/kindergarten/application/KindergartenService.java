package com.meommu.meommuapi.kindergarten.application;

import com.meommu.meommuapi.authentication.dto.AuthInfo;
import com.meommu.meommuapi.kindergarten.dto.EmailRequest;
import com.meommu.meommuapi.kindergarten.dto.KindergartenPasswordUpdateRequest;
import com.meommu.meommuapi.kindergarten.dto.KindergartenResponse;
import com.meommu.meommuapi.kindergarten.dto.KindergartenUpdateRequest;
import com.meommu.meommuapi.kindergarten.dto.MyInfoResponse;
import com.meommu.meommuapi.kindergarten.dto.SignUpRequest;

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

package com.meommu.meommuapi.util;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.meommu.meommuapi.core.auth.repository.RefreshTokenRepository;
import com.meommu.meommuapi.core.auth.service.AuthService;
import com.meommu.meommuapi.core.auth.token.JwtTokenProvider;
import com.meommu.meommuapi.core.diary.repository.DiaryImageRepository;
import com.meommu.meommuapi.core.diary.repository.DiaryRepository;
import com.meommu.meommuapi.core.diary.service.DiaryService;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.core.kindergarten.repository.EmailCodeRepository;
import com.meommu.meommuapi.core.kindergarten.repository.KindergartenRepository;
import com.meommu.meommuapi.core.kindergarten.service.KindergartenService;

@ExtendWith(MockitoExtension.class)
public abstract class ServiceTest {

	@InjectMocks
	protected KindergartenService kindergartenService;

	@InjectMocks
	protected DiaryService diaryService;

	@InjectMocks
	protected AuthService authService;

	@Mock
	protected KindergartenRepository kindergartenRepository;

	@Mock
	protected DiaryRepository diaryRepository;

	@Mock
	protected DiaryImageRepository diaryImageRepository;

	@Mock
	protected Encryptor encryptor;

	@Mock
	protected JwtTokenProvider jwtTokenProvider;

	@Mock
	protected RefreshTokenRepository refreshTokenRepository;

	@Mock
	protected EmailCodeRepository emailCodeRepository;
}
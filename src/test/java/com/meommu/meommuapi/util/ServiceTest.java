package com.meommu.meommuapi.util;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.meommu.meommuapi.authentication.infrastructure.RefreshTokenRepository;
import com.meommu.meommuapi.authentication.application.AuthServiceImpl;
import com.meommu.meommuapi.authentication.configuration.JwtTokenProvider;
import com.meommu.meommuapi.diary.infrastructure.DiaryImageRepository;
import com.meommu.meommuapi.diary.infrastructure.DiaryRepository;
import com.meommu.meommuapi.diary.application.DiaryServiceImpl;
import com.meommu.meommuapi.gpt.application.GptServiceImpl;
import com.meommu.meommuapi.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.kindergarten.infrastructure.EmailCodeRepository;
import com.meommu.meommuapi.kindergarten.infrastructure.KindergartenRepository;
import com.meommu.meommuapi.kindergarten.application.KindergartenServiceImpl;

@ExtendWith(MockitoExtension.class)
public abstract class ServiceTest {

	@InjectMocks
	protected KindergartenServiceImpl kindergartenService;

	@InjectMocks
	protected DiaryServiceImpl diaryService;

	@InjectMocks
	protected AuthServiceImpl authService;

	@InjectMocks
	protected GptServiceImpl gptService;

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
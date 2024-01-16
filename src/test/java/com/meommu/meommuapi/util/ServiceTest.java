package com.meommu.meommuapi.util;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.meommu.meommuapi.core.auth.repository.RefreshTokenRepository;
import com.meommu.meommuapi.core.auth.service.AuthServiceImpl;
import com.meommu.meommuapi.core.auth.token.JwtTokenProvider;
import com.meommu.meommuapi.core.diary.repository.DiaryImageRepository;
import com.meommu.meommuapi.core.diary.repository.DiaryRepository;
import com.meommu.meommuapi.core.diary.service.DiaryServiceImpl;
import com.meommu.meommuapi.core.gpt.service.GptServiceImpl;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.core.kindergarten.repository.EmailCodeRepository;
import com.meommu.meommuapi.core.kindergarten.repository.KindergartenRepository;
import com.meommu.meommuapi.core.kindergarten.service.KindergartenServiceImpl;

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
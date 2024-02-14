package com.meommu.meommuapi.util;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.meommu.meommuapi.auth.repository.RefreshTokenRepository;
import com.meommu.meommuapi.auth.service.AuthServiceImpl;
import com.meommu.meommuapi.auth.token.JwtTokenProvider;
import com.meommu.meommuapi.diary.repository.DiaryImageRepository;
import com.meommu.meommuapi.diary.repository.DiaryRepository;
import com.meommu.meommuapi.diary.service.DiaryServiceImpl;
import com.meommu.meommuapi.gpt.service.GptServiceImpl;
import com.meommu.meommuapi.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.kindergarten.repository.EmailCodeRepository;
import com.meommu.meommuapi.kindergarten.repository.KindergartenRepository;
import com.meommu.meommuapi.kindergarten.service.KindergartenServiceImpl;

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
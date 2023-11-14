package com.meommu.meommuapi.util;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.meommu.meommuapi.auth.service.AuthService;
import com.meommu.meommuapi.auth.token.JwtTokenProvider;
import com.meommu.meommuapi.diary.repository.DiaryImageRepository;
import com.meommu.meommuapi.diary.repository.DiaryRepository;
import com.meommu.meommuapi.diary.service.DiaryService;
import com.meommu.meommuapi.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.kindergarten.repository.KindergartenRepository;
import com.meommu.meommuapi.kindergarten.service.KindergartenService;

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

}
package com.meommu.meommuapi.util;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.meommu.meommuapi.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.kindergarten.repository.KindergartenRepository;
import com.meommu.meommuapi.kindergarten.service.KindergartenService;

@ExtendWith(MockitoExtension.class)
public abstract class ServiceTest {

	@InjectMocks
	protected KindergartenService kindergartenService;

	@Mock
	protected KindergartenRepository kindergartenRepository;

	@Mock
	protected Encryptor encryptor;
}
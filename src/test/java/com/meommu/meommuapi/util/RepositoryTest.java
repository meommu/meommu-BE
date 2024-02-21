package com.meommu.meommuapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.meommu.meommuapi.diary.infrastructure.DiaryImageRepository;
import com.meommu.meommuapi.diary.infrastructure.DiaryRepository;
import com.meommu.meommuapi.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.kindergarten.infrastructure.KindergartenRepository;
import com.meommu.meommuapi.common.configuration.JpaConfig;

@DataJpaTest
@Import(JpaConfig.class)
public abstract class RepositoryTest {

	@Autowired
	protected KindergartenRepository kindergartenRepository;

	@Autowired
	protected DiaryRepository diaryRepository;

	@Autowired
	protected DiaryImageRepository diaryImageRepository;

	protected Encryptor encryptor = new Encryptor();
}
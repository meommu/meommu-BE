package com.meommu.meommuapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.meommu.meommuapi.core.diary.repository.DiaryImageRepository;
import com.meommu.meommuapi.core.diary.repository.DiaryRepository;
import com.meommu.meommuapi.core.kindergarten.domain.embedded.Encryptor;
import com.meommu.meommuapi.core.kindergarten.repository.KindergartenRepository;
import com.meommu.meommuapi.global.config.JpaConfig;

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
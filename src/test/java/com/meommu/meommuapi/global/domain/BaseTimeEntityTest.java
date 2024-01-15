package com.meommu.meommuapi.global.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestJpaConfig.class)
public class BaseTimeEntityTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	public void entityListenersShouldSetCreatedAndLastModifiedDate() {
		// given
		BaseEntity entity = new BaseEntity();
		testEntityManager.persist(entity);
		testEntityManager.flush();

		// when
		BaseEntity foundEntity = testEntityManager.find(BaseEntity.class, entity.getId());

		// then
		assertNotNull(foundEntity.getCreatedAt());
		assertNotNull(foundEntity.getLastModifiedAt());
	}
}
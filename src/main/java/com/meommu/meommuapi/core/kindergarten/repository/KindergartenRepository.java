package com.meommu.meommuapi.core.kindergarten.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meommu.meommuapi.core.kindergarten.domain.Kindergarten;

@Repository
public interface KindergartenRepository extends JpaRepository<Kindergarten, Long> {
	boolean existsByEmailValue(String email);

	Optional<Kindergarten> findByEmailValueAndPasswordValue(String email, String password);

	Optional<Kindergarten> findByEmailValue(String email);
}

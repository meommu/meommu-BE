package com.meommu.meommuapi.guide.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meommu.meommuapi.guide.domain.Guide;

public interface GuideRepository extends JpaRepository<Guide, Long> {
}

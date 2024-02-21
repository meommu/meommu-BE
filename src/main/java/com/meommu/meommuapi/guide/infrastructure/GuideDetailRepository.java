package com.meommu.meommuapi.guide.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meommu.meommuapi.guide.domain.GuideDetail;

public interface GuideDetailRepository extends JpaRepository<GuideDetail, Long> {
	List<GuideDetail> findAllByGuideId(Long guideId);
}

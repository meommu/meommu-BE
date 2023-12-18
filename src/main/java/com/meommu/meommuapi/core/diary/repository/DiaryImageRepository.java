package com.meommu.meommuapi.core.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meommu.meommuapi.core.diary.domain.DiaryImage;

@Repository
public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {
}

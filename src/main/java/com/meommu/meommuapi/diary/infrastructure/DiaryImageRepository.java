package com.meommu.meommuapi.diary.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meommu.meommuapi.diary.domain.DiaryImage;

@Repository
public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {
}

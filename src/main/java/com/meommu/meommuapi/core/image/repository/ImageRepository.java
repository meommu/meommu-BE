package com.meommu.meommuapi.core.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meommu.meommuapi.core.image.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}

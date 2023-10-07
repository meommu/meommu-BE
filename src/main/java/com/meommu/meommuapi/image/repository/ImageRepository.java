package com.meommu.meommuapi.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meommu.meommuapi.image.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}

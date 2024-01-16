package com.meommu.meommuapi.core.image.service;

import java.util.List;

import com.meommu.meommuapi.core.image.dto.ImageResponse;
import com.meommu.meommuapi.core.image.dto.ImageResponses;
import com.meommu.meommuapi.core.image.dto.ImagesSaveRequest;

public interface ImageService {
	ImageResponses findAllById(List<Long> imageIds);

	ImageResponse findById(Long imageId);

	ImageResponses create(ImagesSaveRequest imagesSaveRequest);

	void deleteById(Long imageId);

	void deleteAllById(List<Long> imageIds);

}

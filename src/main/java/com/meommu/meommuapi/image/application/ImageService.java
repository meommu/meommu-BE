package com.meommu.meommuapi.image.application;

import java.util.List;

import com.meommu.meommuapi.image.dto.ImageResponse;
import com.meommu.meommuapi.image.dto.ImageResponses;
import com.meommu.meommuapi.image.dto.ImagesSaveRequest;

public interface ImageService {
	ImageResponses findAllById(List<Long> imageIds);

	ImageResponse findById(Long imageId);

	ImageResponses create(ImagesSaveRequest imagesSaveRequest);

	void deleteById(Long imageId);

	void deleteAllById(List<Long> imageIds);

}

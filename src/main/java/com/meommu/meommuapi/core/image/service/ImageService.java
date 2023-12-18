package com.meommu.meommuapi.core.image.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.core.image.exception.CategoryNotFoundException;
import com.meommu.meommuapi.core.image.exception.ImageNotFoundException;
import com.meommu.meommuapi.core.image.repository.ImageRepository;
import com.meommu.meommuapi.core.image.domain.Image;
import com.meommu.meommuapi.core.image.dto.ImageResponse;
import com.meommu.meommuapi.core.image.dto.ImageResponses;
import com.meommu.meommuapi.core.image.dto.ImagesSaveRequest;
import com.meommu.meommuapi.core.image.util.ImageCategory;
import com.meommu.meommuapi.core.image.util.ImageValidator;

@Service
@Transactional(readOnly = true)
public class ImageService {

	private final ImageRepository imageRepository;
	private final FileManager fileManager;

	public ImageService(ImageRepository imageRepository, FileManager fileManager) {
		this.imageRepository = imageRepository;
		this.fileManager = fileManager;
	}

	public ImageResponses findAllById(List<Long> imageIds) {
		List<Image> images = imageRepository.findAllById(imageIds);
		return ImageResponses.from(images);
	}

	public ImageResponse findById(Long imageId) {
		return ImageResponse.from(getImageById(imageId));
	}

	@Transactional
	public ImageResponses create(ImagesSaveRequest imagesSaveRequest) {
		ImageValidator.validate(imagesSaveRequest.getImages(), imagesSaveRequest.getCategory());

		ImageCategory selectedCategory = getImageCategory(imagesSaveRequest.getCategory());
		List<String> urls = fileManager.upload(imagesSaveRequest.getImages(), selectedCategory.getDirectory());

		List<Image> images = urls.stream()
			.map(url -> Image.of(url))
			.collect(Collectors.toList());

		imageRepository.saveAll(images);

		return ImageResponses.from(images);
	}

	@Transactional
	public void deleteById(Long imageId) {
		Image image = getImageById(imageId);
		fileManager.delete(image.getUrl());
		imageRepository.delete(image);
	}

	@Transactional
	public void deleteAllById(List<Long> imageIds) {
		List<Image> images = imageRepository.findAllById(imageIds);

		for (Image image : images) {
			fileManager.delete(image.getUrl());
		}

		imageRepository.deleteAll(images);
	}

	private Image getImageById(Long id) {
		return imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException());
	}

	private ImageCategory getImageCategory(String category) {
		for (ImageCategory imageCategory : ImageCategory.values()) {
			if (imageCategory.name().equalsIgnoreCase(category.trim())) {
				return imageCategory;
			}
		}
		throw new CategoryNotFoundException();
	}
}

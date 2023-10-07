package com.meommu.meommuapi.image.util;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.meommu.meommuapi.image.exception.CategoryNotFoundException;
import com.meommu.meommuapi.image.exception.InvalidImageCountException;
import com.meommu.meommuapi.image.exception.InvalidImageTypeExeption;

public class ImageValidator {

	private static List<String> validContentTypes = List.of("image/jpeg", "image/png", "image/heic");

	public static void validate(List<MultipartFile> images, String category) {
		validateImageType(images);
		validateCategory(category);
		validateImageCount(images, category);
	}

	private static void validateImageType(List<MultipartFile> images) {
		for (MultipartFile image : images) {
			String contentType = image.getContentType();
			if (!validContentTypes.contains(contentType)) {
				throw new InvalidImageTypeExeption();
			}
		}
	}

	private static void validateCategory(String category) {
		try {
			ImageCategory.valueOf(category.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new CategoryNotFoundException();
		}
	}

	private static void validateImageCount(List<MultipartFile> images, String category) {
		ImageCategory imageCategory = ImageCategory.valueOf(category.toUpperCase());
		int maxFiles = imageCategory.getMaxFiles();
		if (images.size() > maxFiles) {
			throw new InvalidImageCountException();
		}
	}
}
package com.meommu.meommuapi.core.image.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meommu.meommuapi.core.image.service.ImageService;
import com.meommu.meommuapi.core.image.dto.ImageResponse;
import com.meommu.meommuapi.core.image.dto.ImageResponses;
import com.meommu.meommuapi.core.image.dto.ImagesSaveRequest;

import jakarta.validation.Valid;

@RestController
public class ImageController {

	private final ImageService imageService;

	public ImageController(ImageService imageService) {
		this.imageService = imageService;
	}

	@GetMapping("/api/v1/images")
	public ImageResponses findImages(@RequestParam("id") List<Long> imageIds) {
		return imageService.findAllById(imageIds);
	}

	@GetMapping("/api/v1/images/{imageId}")
	public ImageResponse findImage(@PathVariable Long imageId) {
		return imageService.findById(imageId);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/images")
	public ImageResponses createImages(@Valid ImagesSaveRequest imagesSaveRequest) {
		return imageService.create(imagesSaveRequest);
	}

	@DeleteMapping("/api/v1/images/{imageId}")
	public void deleteImage(@PathVariable Long imageId) {
		imageService.deleteById(imageId);
	}

	@DeleteMapping("/api/v1/images")
	public void deleteImages(@RequestParam("id") List<Long> imageIds) {
		imageService.deleteAllById(imageIds);
	}
}

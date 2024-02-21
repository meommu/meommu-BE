package com.meommu.meommuapi.image.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.meommu.meommuapi.image.domain.Image;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageResponses {

	List<ImageResponse> images;

	private ImageResponses() {
	}

	@Builder
	private ImageResponses(List<ImageResponse> images) {
		this.images = images;
	}

	public static ImageResponses from(List<Image> images) {
		List<ImageResponse> imageResponses = images.stream()
			.map(ImageResponse::from)
			.collect(Collectors.toList());
		return ImageResponses.builder()
			.images(imageResponses)
			.build();
	}
}

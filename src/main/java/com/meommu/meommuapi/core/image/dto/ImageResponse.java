package com.meommu.meommuapi.core.image.dto;

import com.meommu.meommuapi.core.image.domain.Image;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageResponse {

	Long id;

	String url;

	private ImageResponse() {
	}

	@Builder
	private ImageResponse(Long id, String url) {
		this.id = id;
		this.url = url;
	}

	public static ImageResponse from(Image image) {
		return ImageResponse.builder()
			.id(image.getId())
			.url(image.getUrl())
			.build();
	}

}

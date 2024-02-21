package com.meommu.meommuapi.guide.application.dto;

import com.meommu.meommuapi.guide.domain.Guide;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GuideResponse {

	private Long id;

	private String guide;

	private String description;

	private GuideResponse() {
	}

	@Builder
	private GuideResponse(Long id, String guide, String description) {
		this.id = id;
		this.guide = guide;
		this.description = description;
	}

	public static GuideResponse from(Guide guide) {
		return new GuideResponse(guide.getId(), guide.getContent(), guide.getDescription());
	}
}

package com.meommu.meommuapi.guide.dto;

import com.meommu.meommuapi.guide.domain.Guide;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GuideResponse {
	private Long id;
	private String guide;

	private GuideResponse() {
	}

	@Builder
	private GuideResponse(Long id, String guide) {
		this.id = id;
		this.guide = guide;
	}

	public static GuideResponse from(Guide guide) {
		return new GuideResponse(guide.getId(), guide.getContent());
	}
}

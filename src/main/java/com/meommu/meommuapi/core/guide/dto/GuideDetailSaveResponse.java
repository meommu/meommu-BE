package com.meommu.meommuapi.core.guide.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GuideDetailSaveResponse {

	private Long savedId;

	private GuideDetailSaveResponse() {
	}

	@Builder
	private GuideDetailSaveResponse(Long savedId) {
		this.savedId = savedId;
	}

	public static GuideDetailSaveResponse from(Long savedId) {
		return new GuideDetailSaveResponse(savedId);
	}
}

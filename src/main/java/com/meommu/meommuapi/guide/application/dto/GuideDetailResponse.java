package com.meommu.meommuapi.guide.application.dto;

import com.meommu.meommuapi.guide.domain.GuideDetail;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GuideDetailResponse {
	private Long id;
	private String detail;

	private GuideDetailResponse() {
	}

	@Builder
	private GuideDetailResponse(Long id, String detail) {
		this.id = id;
		this.detail = detail;
	}

	public static GuideDetailResponse from(GuideDetail guideDetail) {
		return new GuideDetailResponse(guideDetail.getId(), guideDetail.getContent());
	}
}

package com.meommu.meommuapi.core.guide.dto;

import java.util.ArrayList;
import java.util.List;

import com.meommu.meommuapi.core.guide.domain.GuideDetail;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GuideDetailResponses {

	private List<GuideDetailResponse> details;

	private GuideDetailResponses() {
	}

	@Builder
	private GuideDetailResponses(List<GuideDetailResponse> details) {
		this.details = details;
	}

	public static GuideDetailResponses from(List<GuideDetail> guideDetails) {
		List<GuideDetailResponse> diaryGuideDetailResponses = new ArrayList<>();
		for (GuideDetail guideDetail : guideDetails) {
			diaryGuideDetailResponses.add(GuideDetailResponse.from(guideDetail));
		}
		return new GuideDetailResponses(diaryGuideDetailResponses);
	}
}

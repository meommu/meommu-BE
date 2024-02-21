package com.meommu.meommuapi.guide.application.dto;

import java.util.ArrayList;
import java.util.List;

import com.meommu.meommuapi.guide.domain.Guide;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GuideResponses {

	private List<GuideResponse> guides;

	private GuideResponses() {
	}

	@Builder
	private GuideResponses(List<GuideResponse> guides) {
		this.guides = guides;
	}

	public static GuideResponses from(List<Guide> gptGuides) {
		List<GuideResponse> guideResponses = new ArrayList<>();
		for (Guide gptGuide : gptGuides) {
			guideResponses.add(GuideResponse.from(gptGuide));
		}
		return new GuideResponses(guideResponses);
	}
}

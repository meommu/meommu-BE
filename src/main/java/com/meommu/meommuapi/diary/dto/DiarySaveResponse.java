package com.meommu.meommuapi.diary.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiarySaveResponse {
	private Long savedId;

	private DiarySaveResponse() {
	}

	@Builder
	private DiarySaveResponse(Long savedId) {
		this.savedId = savedId;
	}

	public static DiarySaveResponse from(Long savedId) {
		return new DiarySaveResponse(savedId);
	}
}

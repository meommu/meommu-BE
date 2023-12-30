package com.meommu.meommuapi.core.diary.dto;

import com.meommu.meommuapi.core.diary.domain.Diary;

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

	public static DiarySaveResponse from(Diary diary) {
		return new DiarySaveResponse(diary.getId());
	}
}
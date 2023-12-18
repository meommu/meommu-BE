package com.meommu.meommuapi.core.diary.dto;

import com.meommu.meommuapi.core.diary.domain.Diary;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiaryUUIDResponse {
	private String uuid;

	private DiaryUUIDResponse() {
	}

	@Builder
	private DiaryUUIDResponse(String uuid) {
		this.uuid = uuid;
	}

	public static DiaryUUIDResponse from(Diary diary) {
		return new DiaryUUIDResponse(diary.getUUID());
	}
}

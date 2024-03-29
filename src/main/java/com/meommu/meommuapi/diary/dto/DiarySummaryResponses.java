package com.meommu.meommuapi.diary.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.meommu.meommuapi.diary.domain.Diary;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiarySummaryResponses {

	private List<DiarySummaryResponse> diaries;

	private DiarySummaryResponses() {
	}

	@Builder
	private DiarySummaryResponses(List<DiarySummaryResponse> diarySimpleResponses) {
		this.diaries = diarySimpleResponses;
	}

	public static DiarySummaryResponses from(List<Diary> diaries) {
		List<DiarySummaryResponse> diarySimpleResponses = diaries.stream()
			.map(DiarySummaryResponse::from)
			.collect(Collectors.toList());
		return new DiarySummaryResponses(diarySimpleResponses);
	}
}

package com.meommu.meommuapi.diary.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.meommu.meommuapi.diary.domain.Diary;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiaryResponses {

	private List<DiaryResponse> diaries;

	private DiaryResponses() {
	}

	@Builder
	private DiaryResponses(List<DiaryResponse> diaryResponses) {
		this.diaries = diaryResponses;
	}

	public static DiaryResponses from(List<Diary> diaries) {
		List<DiaryResponse> diaryResponses = diaries.stream()
			.map(DiaryResponse::from)
			.collect(Collectors.toList());
		return new DiaryResponses(diaryResponses);
	}
}

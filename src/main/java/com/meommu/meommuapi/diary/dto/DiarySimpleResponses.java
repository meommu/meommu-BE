package com.meommu.meommuapi.diary.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.meommu.meommuapi.diary.domain.Diary;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiarySimpleResponses {

	private List<DiarySimpleResponse> diaries;

	private DiarySimpleResponses() {
	}

	@Builder
	private DiarySimpleResponses(List<DiarySimpleResponse> diarySimpleResponses) {
		this.diaries = diarySimpleResponses;
	}

	public static DiarySimpleResponses from(List<Diary> diaries) {
		List<DiarySimpleResponse> diarySimpleResponses = diaries.stream()
			.map(DiarySimpleResponse::from)
			.collect(Collectors.toList());
		return new DiarySimpleResponses(diarySimpleResponses);
	}
}

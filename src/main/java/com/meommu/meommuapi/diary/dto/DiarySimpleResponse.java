package com.meommu.meommuapi.diary.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.meommu.meommuapi.diary.domain.Diary;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiarySimpleResponse {
	private Long id;
	private LocalDate date;
	private LocalDateTime createdAt;
	private List<Long> imageIds;

	private DiarySimpleResponse() {
	}

	@Builder
	private DiarySimpleResponse(Long id, LocalDate date, LocalDateTime createdAt, List<Long> imageIds) {
		this.id = id;
		this.date = date;
		this.createdAt = createdAt;
		this.imageIds = imageIds;
	}

	public static DiarySimpleResponse from(Diary diary) {
		return DiarySimpleResponse.builder()
			.id(diary.getId())
			.date(diary.getDate())
			.createdAt(diary.getCreatedAt())
			.imageIds(diary.getImageIds())
			.build();
	}
}

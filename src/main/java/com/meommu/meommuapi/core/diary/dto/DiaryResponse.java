package com.meommu.meommuapi.core.diary.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.meommu.meommuapi.core.diary.domain.Diary;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiaryResponse {
	private Long id;
	private LocalDate date;
	private String dogName;
	private LocalDateTime createdAt;
	private List<Long> imageIds;
	private String title;
	private String content;

	private DiaryResponse() {
	}

	@Builder
	private DiaryResponse(Long id, LocalDate date, String dogName, LocalDateTime createdAt, List<Long> imageIds,
		String title, String content) {
		this.id = id;
		this.date = date;
		this.dogName = dogName;
		this.createdAt = createdAt;
		this.imageIds = imageIds;
		this.title = title;
		this.content = content;
	}

	public static DiaryResponse from(Diary diary) {
		return DiaryResponse.builder()
			.id(diary.getId())
			.date(diary.getDate())
			.dogName(diary.getDogName())
			.createdAt(diary.getCreatedAt())
			.imageIds(diary.getImageIds())
			.title(diary.getTitle())
			.content(diary.getContent())
			.build();
	}
}

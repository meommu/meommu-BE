package com.meommu.meommuapi.core.diary.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.meommu.meommuapi.core.diary.domain.Diary;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiarySummaryResponse {
	private Long id;
	private LocalDate date;
	private LocalDateTime createdAt;
	private List<Long> imageIds;

	private DiarySummaryResponse() {
	}

	@Builder
	private DiarySummaryResponse(Long id, LocalDate date, LocalDateTime createdAt, List<Long> imageIds) {
		this.id = id;
		this.date = date;
		this.createdAt = createdAt;
		this.imageIds = imageIds;
	}

	public static DiarySummaryResponse from(Diary diary) {
		return DiarySummaryResponse.builder()
			.id(diary.getId())
			.date(diary.getDate())
			.createdAt(diary.getCreatedAt())
			.imageIds(diary.getImageIds())
			.build();
	}
}

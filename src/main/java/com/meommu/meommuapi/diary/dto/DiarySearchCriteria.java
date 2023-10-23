package com.meommu.meommuapi.diary.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiarySearchCriteria {

	private Integer year;

	private Integer month;

	private DiarySearchCriteria() {
	}

	@Builder
	private DiarySearchCriteria(Integer year, Integer month) {
		this.year = year == null ? LocalDate.now().getYear() : year;
		this.month = month == null ? LocalDate.now().getMonth().getValue() : month;
	}
}

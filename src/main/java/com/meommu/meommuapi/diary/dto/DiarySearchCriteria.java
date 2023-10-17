package com.meommu.meommuapi.diary.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DiarySearchCriteria {

	@NotBlank(message = "연도는 필수로 입력해야 합니다.")
	private Integer year;

	@NotBlank(message = "월은 필수로 입력해야 합니다.")
	private Integer month;

	private DiarySearchCriteria() {
	}

	@Builder
	public DiarySearchCriteria(int year, int month) {
		this.year = year;
		this.month = month;
	}
}

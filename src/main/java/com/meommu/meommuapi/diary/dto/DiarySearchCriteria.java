package com.meommu.meommuapi.diary.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiarySearchCriteria {

	@NotNull(message = "연도는 필수로 입력해야합니다.")
	private Integer year;

	@NotNull(message = "월은 필수로 입력해야합니다.")
	private Integer month;

	private DiarySearchCriteria() {
	}

	@Builder
	private DiarySearchCriteria(Integer year, Integer month) {
		this.year = year;
		this.month = month;
	}
}

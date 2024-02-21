package com.meommu.meommuapi.diary.dto;

import static com.meommu.meommuapi.common.util.Constant.*;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiarySaveRequest {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FORMAT_LOCAL_DATE, timezone = "Asia/Seoul")
	private LocalDate date;

	private String dogName;

	private String title;

	private String content;

	private List<Long> imageIds;

	private DiarySaveRequest() {
	}

	@Builder
	private DiarySaveRequest(LocalDate date, String dogName, String title, String content, List<Long> imageIds) {
		this.date = date;
		this.dogName = dogName;
		this.title = title;
		this.content = content;
		this.imageIds = imageIds;
	}
}

package com.meommu.meommuapi.notice.dto;

import java.time.LocalDateTime;

import com.meommu.meommuapi.notice.domain.Notice;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeResponse {

	private Long id;

	private String title;

	private String content;

	private LocalDateTime createdAt;

	private NoticeResponse() {
	}

	@Builder
	private NoticeResponse(Long id, String title, String content, LocalDateTime createdAt) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
	}

	public static NoticeResponse from(Notice notice) {
		return  NoticeResponse.builder()
			.id(notice.getId())
			.title(notice.getTitle())
			.content(notice.getContent())
			.createdAt(notice.getCreatedAt())
			.build();
	}
}

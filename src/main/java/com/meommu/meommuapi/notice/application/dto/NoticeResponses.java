package com.meommu.meommuapi.notice.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.meommu.meommuapi.notice.domain.Notice;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NoticeResponses {

	private List<NoticeResponse> notices;

	private NoticeResponses() {
	}

	@Builder
	private NoticeResponses(List<NoticeResponse> noticeResponses) {
		this.notices = noticeResponses;
	}

	public static NoticeResponses from(List<Notice> notices) {
		List<NoticeResponse> noticeResponses = notices.stream()
			.map(NoticeResponse::from)
			.collect(Collectors.toList());
		return new NoticeResponses(noticeResponses);
	}
}

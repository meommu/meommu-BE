package com.meommu.meommuapi.notice.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meommu.meommuapi.notice.application.dto.NoticeResponses;
import com.meommu.meommuapi.notice.application.NoticeService;

@RestController
public class NoticeController {

	private final NoticeService noticeService;

	public NoticeController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@GetMapping("/api/v1/notices")
	public NoticeResponses findNotices() {
		return noticeService.findNotices();
	}
}

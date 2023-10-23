package com.meommu.meommuapi.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.notice.domain.Notice;
import com.meommu.meommuapi.notice.dto.NoticeResponses;
import com.meommu.meommuapi.notice.repository.NoticeRepository;

@Service
@Transactional(readOnly = true)
public class NoticeService {

	private final NoticeRepository noticeRepository;

	public NoticeService(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}

	public NoticeResponses findNotices() {
		List<Notice> notices = noticeRepository.findAllByOrderByCreatedAtDesc();
		return NoticeResponses.from(notices);
	}
}

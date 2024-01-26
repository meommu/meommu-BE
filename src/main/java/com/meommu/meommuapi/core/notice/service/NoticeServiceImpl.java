package com.meommu.meommuapi.core.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.core.notice.domain.Notice;
import com.meommu.meommuapi.core.notice.dto.NoticeResponses;
import com.meommu.meommuapi.core.notice.repository.NoticeRepository;

@Service
@Transactional(readOnly = true)
public class NoticeServiceImpl implements NoticeService{

	private final NoticeRepository noticeRepository;

	public NoticeServiceImpl(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}

	@Override
	public NoticeResponses findNotices() {
		List<Notice> notices = noticeRepository.findAllByOrderByCreatedAtDesc();
		return NoticeResponses.from(notices);
	}
}

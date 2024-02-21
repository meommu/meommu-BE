package com.meommu.meommuapi.notice.application;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.notice.domain.Notice;
import com.meommu.meommuapi.notice.application.dto.NoticeResponses;
import com.meommu.meommuapi.notice.infrastructure.NoticeRepository;

@Service
@Transactional(readOnly = true)
public class NoticeServiceImpl implements NoticeService{

	private final NoticeRepository noticeRepository;

	public NoticeServiceImpl(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}

	@Override
	@Cacheable(cacheNames = "notice")
	public NoticeResponses findNotices() {
		List<Notice> notices = noticeRepository.findAllByOrderByCreatedAtDesc();
		return NoticeResponses.from(notices);
	}
}

package com.meommu.meommuapi.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meommu.meommuapi.notice.domain.Notice;
import com.meommu.meommuapi.notice.dto.NoticeResponses;
import com.meommu.meommuapi.notice.repository.NoticeRepository;

@Service
@Transactional(readOnly = true)
public class NoticeServiceImpl implements NoticeService{

	private final NoticeRepository noticeRepository;

	public NoticeServiceImpl(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}

	@Override
	// @Cacheable(cacheNames = "notice")
	public NoticeResponses findNotices() {
		System.out.println("캐시 이용 전");
		List<Notice> notices = noticeRepository.findAllByOrderByCreatedAtDesc();
		return NoticeResponses.from(notices);
	}
}

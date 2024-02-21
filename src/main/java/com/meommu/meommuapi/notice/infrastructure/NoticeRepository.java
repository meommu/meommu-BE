package com.meommu.meommuapi.notice.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meommu.meommuapi.notice.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
	List<Notice> findAllByOrderByCreatedAtDesc();
}

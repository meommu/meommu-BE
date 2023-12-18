package com.meommu.meommuapi.core.notice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meommu.meommuapi.core.notice.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
	List<Notice> findAllByOrderByCreatedAtDesc();
}

package com.meommu.meommuapi.core.diary.domain;

import com.meommu.meommuapi.global.domain.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

@Entity
public class DiaryImage extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long imageId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "diary_id")
	private Diary diary;

	protected DiaryImage() {
	}

	@Builder
	private DiaryImage(Long imageId, Diary diary) {
		this.imageId = imageId;
		this.diary = diary;
	}

	public Long getId() {
		return id;
	}

	public Long getImageId() {
		return imageId;
	}

	public Diary getDiary() {
		return diary;
	}
}
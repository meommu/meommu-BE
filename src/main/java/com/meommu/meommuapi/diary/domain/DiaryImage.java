package com.meommu.meommuapi.diary.domain;

import com.meommu.meommuapi.common.domain.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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

	private DiaryImage(Long imageId) {
		this.imageId = imageId;
	}

	public static DiaryImage of(Long imageId) {
		return new DiaryImage(imageId);
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

	public void setDiary(Diary diary) {
		if (this.diary != null) {
			this.diary.getImages().remove(this);
		}
		this.diary = diary;
		diary.getDiaryImages().add(this);
	}
}
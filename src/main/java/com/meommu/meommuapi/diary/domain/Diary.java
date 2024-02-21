package com.meommu.meommuapi.diary.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.meommu.meommuapi.diary.domain.embedded.Content;
import com.meommu.meommuapi.diary.domain.embedded.DogName;
import com.meommu.meommuapi.diary.domain.embedded.Title;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;
import com.meommu.meommuapi.common.domain.BaseTimeEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Entity
public class Diary extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private DogName dogName;

	@NotNull
	private String uuid;

	@NotNull
	private Title title;

	@NotNull
	private Content content;

	@NotNull
	private LocalDate date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kindergarten_id")
	private Kindergarten kindergarten;

	@OneToMany(mappedBy = "diary", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DiaryImage> diaryImages = new ArrayList<>();

	protected Diary() {
	}

	@Builder
	private Diary(String dogName, String title, String content, LocalDate date, Kindergarten kindergarten) {
		this.dogName = DogName.from(dogName);
		this.title = Title.from(title);
		this.content = Content.from(content);
		this.date = date;
		this.kindergarten = kindergarten;
		this.uuid = UUID.randomUUID().toString();
	}

	public static Diary of(String dogName, String title, String content, LocalDate date, Kindergarten kindergarten) {
		return new Diary(dogName, title, content, date, kindergarten);
	}

	public Long getId() {
		return id;
	}

	public String getDogName() {
		return dogName.getValue();
	}

	public String getTitle() {
		return title.getValue();
	}

	public String getContent() {
		return content.getValue();
	}

	public LocalDate getDate() {
		return date;
	}

	public List<DiaryImage> getDiaryImages() {
		return diaryImages;
	}

	public Kindergarten getKindergarten() {
		return kindergarten;
	}

	public List<DiaryImage> getImages() {
		return Collections.unmodifiableList(diaryImages);
	}

	public List<Long> getImageIds() {
		List<Long> imageIds = new ArrayList<>();
		for (DiaryImage diaryImage : diaryImages) {
			imageIds.add(diaryImage.getImageId());
		}
		return imageIds;
	}

	public String getUUID() {
		return this.uuid;
	}

	public void updateTitle(String value) {
		this.title = Title.from(value);
	}

	public void updateContent(String value) {
		this.content = Content.from(value);
	}

	public void updateDate(LocalDate date) {
		this.date = date;
	}

	public void updateDogName(String dogName) {
		this.dogName = DogName.from(dogName);
	}

	public void updateImages(List<DiaryImage> newDiaryImages) {
		this.diaryImages.clear();

		for (DiaryImage newDiaryImage : newDiaryImages) {
			addDiaryImage(newDiaryImage);
		}
	}

	public void updateUUID() {
		this.uuid = UUID.randomUUID().toString();
	}

	private void addDiaryImage(DiaryImage diaryImage) {
		if (diaryImage != null) {
			this.diaryImages.add(diaryImage);
		}
	}

}

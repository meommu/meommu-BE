package com.meommu.meommuapi.core.notice.domain;

import com.meommu.meommuapi.core.notice.domain.embedded.Content;
import com.meommu.meommuapi.core.notice.domain.embedded.Title;
import com.meommu.meommuapi.global.domain.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

@Entity
public class Notice extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Title title;

	private Content content;

	protected Notice() {
	}

	@Builder
	private Notice(Long id, String title, String content) {
		this.id = id;
		this.title = Title.from(title);
		this.content = Content.from(content);
	}

	public static Notice of(String title, String content) {
		return Notice.builder()
			.title(title)
			.content(content)
			.build();
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title.getValue();
	}

	public String getContent() {
		return content.getValue();
	}
}

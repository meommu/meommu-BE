package com.meommu.meommuapi.guide.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.meommu.meommuapi.guide.domain.embedded.Content;
import com.meommu.meommuapi.common.domain.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
@SQLDelete(sql = "UPDATE guide SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class Guide extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Content content;

	@NotNull
	private String description;

	@NotNull
	private boolean deleted = Boolean.FALSE;

	protected Guide() {
	}

	private Guide(String content) {
		this.content = Content.from(content);
	}

	public static Guide of(String content) {
		return new Guide(content);
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content.getValue();
	}

	public String getDescription() {
		return description;
	}

	public void updateContent(String value) {
		this.content = Content.from(value);
	}
}

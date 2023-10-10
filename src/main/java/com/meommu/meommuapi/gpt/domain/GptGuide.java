package com.meommu.meommuapi.gpt.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.meommu.meommuapi.common.domain.BaseTimeEntity;
import com.meommu.meommuapi.gpt.domain.embedded.Content;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@SQLDelete(sql = "UPDATE gpt_guide SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class GptGuide extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Content content;

	@NotNull
	private boolean deleted = Boolean.FALSE;

	protected GptGuide() {
	}

	private GptGuide(String content) {
		this.content = Content.from(content);
	}

	public static GptGuide of(String content) {
		return new GptGuide(content);
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content.getValue();
	}

	public void updateContent(String value) {
		this.content = Content.from(value);
	}
}

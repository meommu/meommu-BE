package com.meommu.meommuapi.gpt.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.meommu.meommuapi.common.domain.BaseTimeEntity;
import com.meommu.meommuapi.gpt.domain.embedded.Content;
import com.meommu.meommuapi.kindergarten.domain.Kindergarten;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(indexes = {@Index(columnList = "guide_id")})
@SQLDelete(sql = "UPDATE gpt_guide_detail SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class GptGuideDetail extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Content content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "guide_id")
	private GptGuide gptGuide;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "kindergarten_id")
	private Kindergarten kindergarten;

	@NotNull
	private boolean deleted = Boolean.FALSE;

	protected GptGuideDetail() {
	}

	private GptGuideDetail(String content, GptGuide gptGuide, Kindergarten kindergarten) {
		this.content = Content.from(content);
		this.gptGuide = gptGuide;
		this.kindergarten = kindergarten;
	}

	public static GptGuideDetail of(String content, GptGuide gptGuide, Kindergarten kindergarten) {
		return new GptGuideDetail(content, gptGuide, kindergarten);
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content.getValue();
	}

	public GptGuide getGptGuide() {
		return gptGuide;
	}

	public Kindergarten getKindergarten() {
		return kindergarten;
	}

	public void updateContent(String value) {
		this.content = Content.from(value);
	}
}

package com.meommu.meommuapi.core.guide.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.meommu.meommuapi.core.guide.domain.embedded.Content;
import com.meommu.meommuapi.global.domain.BaseTimeEntity;

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
@SQLDelete(sql = "UPDATE guide_detail SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class GuideDetail extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Content content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "guide_id")
	private Guide guide;

	@NotNull
	private boolean deleted = Boolean.FALSE;

	protected GuideDetail() {
	}

	private GuideDetail(String content, Guide gptGuide) {
		this.content = Content.from(content);
		this.guide = gptGuide;
	}

	public static GuideDetail of(String content, Guide guide) {
		return new GuideDetail(content, guide);
	}

	public Long getId() {
		return id;
	}

	public String getContent() {
		return content.getValue();
	}

	public Guide getGuide() {
		return guide;
	}

	public void updateContent(String value) {
		this.content = Content.from(value);
	}
}

package com.meommu.meommuapi.image.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.meommu.meommuapi.common.domain.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
@SQLDelete(sql = "UPDATE image SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class Image extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String url;

	@NotNull
	private boolean deleted = Boolean.FALSE;

	protected Image() {
	}

	private Image(String url) {
		this.url = url;
	}

	public static Image of(String url) {
		return new Image(url);
	}

	public Long getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}
}

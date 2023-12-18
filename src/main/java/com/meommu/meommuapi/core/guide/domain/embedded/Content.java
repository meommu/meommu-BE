package com.meommu.meommuapi.core.guide.domain.embedded;

import java.util.Objects;

import com.meommu.meommuapi.core.guide.exception.InvalidGuideFormatException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Content {

	private static final int LIMIT_LENGTH = 15;

	@Column(name = "content")
	private String value;

	protected Content() {
	}

	private Content(String value) {
		this.value = value;
	}

	public static Content from(String value) {
		String trimValue = value.trim();
		validate(trimValue);
		return new Content(trimValue);
	}

	private static void validate(String value) {
		if (value.length() > LIMIT_LENGTH || value.length() == 0) {
			throw new InvalidGuideFormatException();
		}
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Content title = (Content)o;
		return Objects.equals(value, title.value);
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}


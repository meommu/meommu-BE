package com.meommu.meommuapi.core.diary.domain.embedded;

import java.util.Objects;

import com.meommu.meommuapi.core.diary.exception.InvalidContentFormatException;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Content {

	private static final int LIMIT_LENGTH = 1000;

	@Column(name = "content", columnDefinition = "TEXT")
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
		if (value == null || value.length() > LIMIT_LENGTH || value.length() == 0) {
			throw new InvalidContentFormatException();
		}
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Content content = (Content)o;
		return Objects.equals(value, content.value);
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}